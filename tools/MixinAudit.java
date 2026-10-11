import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/** Checks Mixin targets against the build's Minecraft bytecode without launching the game. */
public final class MixinAudit {
    static final Map<String, ClassNode> classes = new HashMap<>();
    static final List<String> errors = new ArrayList<>();
    static int checks;
    static ClassNode read(byte[] bytes) { var c=new ClassNode(); new ClassReader(bytes).accept(c,0); return c; }
    static List<AnnotationNode> annotations(List<AnnotationNode> a, List<AnnotationNode> b) { var r=new ArrayList<AnnotationNode>();if(a!=null)r.addAll(a);if(b!=null)r.addAll(b);return r; }
    static AnnotationNode annotation(List<AnnotationNode> a,String simple) { return a.stream().filter(n->n.desc.endsWith('/'+simple+';')).findFirst().orElse(null); }
    static Object value(AnnotationNode a,String name,Object fallback) { if(a==null||a.values==null)return fallback;for(int i=0;i<a.values.size();i+=2)if(a.values.get(i).equals(name))return a.values.get(i+1);return fallback; }
    static void require(boolean condition,String message) { checks++;if(!condition)errors.add(message); }
    static MethodNode method(ClassNode c,String name,String desc) {
        if(c==null)return null;
        for(var m:c.methods)if(m.name.equals(name)&&(desc==null||m.desc.equals(desc)))return m;
        return method(classes.get(c.superName),name,desc);
    }
    static FieldNode field(ClassNode c,String name) {
        if(c==null)return null;for(var f:c.fields)if(f.name.equals(name))return f;return field(classes.get(c.superName),name);
    }
    static String inferred(String method,String... prefixes) {
        for(var p:prefixes)if(method.startsWith(p)&&method.length()>p.length()){String s=method.substring(p.length());return Character.toLowerCase(s.charAt(0))+s.substring(1);}return method;
    }
    static void audit(ClassNode mixin) {
        var mix=annotation(annotations(mixin.visibleAnnotations,mixin.invisibleAnnotations),"Mixin");if(mix==null)return;
        var targets=(List<Type>)value(mix,"value",List.of());
        for(var t:targets) {
            String context=mixin.name+" -> "+t.getInternalName();var target=classes.get(t.getInternalName());require(target!=null,context+" missing target class");if(target==null)continue;
            for(var f:mixin.fields)if(annotation(annotations(f.visibleAnnotations,f.invisibleAnnotations),"Shadow")!=null){var tf=field(target,f.name);require(tf!=null&&tf.desc.equals(f.desc),context+" shadow field "+f.name+f.desc);}
            for(var m:mixin.methods) {
                var a=annotations(m.visibleAnnotations,m.invisibleAnnotations);
                for(String kind:List.of("Overwrite","Shadow"))if(annotation(a,kind)!=null)require(method(target,m.name,m.desc)!=null,context+" "+kind+" "+m.name+m.desc);
                var accessor=annotation(a,"Accessor");if(accessor!=null){String n=(String)value(accessor,"value",inferred(m.name,"get","set","is"));var f=field(target,n);Type[] args=Type.getArgumentTypes(m.desc);String desc=args.length==0?Type.getReturnType(m.desc).getDescriptor():args[0].getDescriptor();require(f!=null&&f.desc.equals(desc),context+" accessor "+n+" "+desc);}
                var invoker=annotation(a,"Invoker");if(invoker!=null){String n=(String)value(invoker,"value",inferred(m.name,"call","invoke"));String desc=m.desc;if(n.equals("<init>"))desc=Type.getMethodDescriptor(Type.VOID_TYPE,Type.getArgumentTypes(desc));require(method(target,n,desc)!=null,context+" invoker "+n+desc);}
                var inject=annotation(a,"Inject");if(inject==null)continue;
                for(String selector:(List<String>)value(inject,"method",List.of())) {
                    int paren=selector.indexOf('(');String name=paren<0?selector:selector.substring(0,paren),desc=paren<0?null:selector.substring(paren);
                    var tm=method(target,name,desc);require(tm!=null,context+" injection "+selector);if(tm==null)continue;
                    var args=Type.getArgumentTypes(m.desc);var targetArgs=Type.getArgumentTypes(tm.desc);int captured=0;
                    while(captured<args.length&&!args[captured].getClassName().startsWith("org.spongepowered.asm.mixin.injection.callback."))captured++;
                    if(captured>0){require(captured==targetArgs.length,context+" handler arguments "+m.name+m.desc+" expected "+tm.desc);for(int i=0;i<Math.min(captured,targetArgs.length);i++)require(args[i].equals(targetArgs[i]),context+" handler argument type "+m.name+" #"+i);}
                    for(var at:(List<AnnotationNode>)value(inject,"at",List.of())) {
                        String kind=(String)value(at,"value","");String call=(String)value(at,"target","");if(!kind.equals("INVOKE")||call.isEmpty())continue;
                        boolean found=false;for(var ins:tm.instructions)if(ins instanceof MethodInsnNode mi&&call.equals("L"+mi.owner+";"+mi.name+mi.desc)){found=true;break;}
                        require(found,context+" injection point "+m.name+" calls "+call);
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        var mixins=new ArrayList<ClassNode>();
        for(String entry:args) {
            Path p=Path.of(entry);
            if(Files.isDirectory(p)){try(var paths=Files.walk(p)){for(var f:paths.filter(f->f.toString().endsWith(".class")).toList()){var c=read(Files.readAllBytes(f));classes.put(c.name,c);if(c.name.startsWith("nonamecrackers2/endertrigon/mixin/"))mixins.add(c);}}}
            else if(p.toString().endsWith(".jar")){try(var jar=new JarFile(p.toFile())){var e=jar.entries();while(e.hasMoreElements()){var f=e.nextElement();if((f.getName().startsWith("net/minecraft/")||f.getName().startsWith("nonamecrackers2/endertrigon/"))&&f.getName().endsWith(".class")){var c=read(jar.getInputStream(f).readAllBytes());classes.put(c.name,c);if(c.name.startsWith("nonamecrackers2/endertrigon/mixin/"))mixins.add(c);}}}}
        }
        for(var c:mixins)audit(c);
        if(mixins.isEmpty())throw new IllegalStateException("No compiled mixins found");
        if(!errors.isEmpty())throw new IllegalStateException(String.join("\n",errors));
        System.out.println("Mixin audit passed: "+mixins.size()+" classes, "+checks+" target/signature/instruction checks.");
    }
}
