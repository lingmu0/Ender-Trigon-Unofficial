/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 26.1.2 Fabric unofficial port.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package nonamecrackers2.endertrigon.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.model.monster.dragon.EnderDragonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
import nonamecrackers2.endertrigon.client.renderer.entity.DragonRendererAdditions;
@Mixin(EnderDragonModel.class)
public class MixinEnderDragonModel {
    @Unique private ModelPart[] endertrigon$heads;
    @Unique private ModelPart[][] endertrigon$necks;
    @Inject(method="<init>", at=@At("TAIL"))
    private void init(ModelPart root, CallbackInfo ci) {
        endertrigon$heads=new ModelPart[2]; endertrigon$necks=new ModelPart[2][5];
        for(int j=0;j<2;j++){ endertrigon$heads[j]=root.getChild("trigon_head"+j); for(int i=0;i<5;i++) endertrigon$necks[j][i]=root.getChild("trigon_neck"+j+"_"+i); }
    }
    @Inject(method="setupAnim(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;)V", at=@At("TAIL"))
    private void animate(EnderDragonRenderState state, CallbackInfo ci) { DragonRendererAdditions.animate(state,endertrigon$heads,endertrigon$necks); }
}
