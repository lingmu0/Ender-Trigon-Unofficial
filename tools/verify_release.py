"""Validate an Ender Trigon release jar without starting Minecraft (Python 3.9+)."""
import argparse
import hashlib
import json
import re
import struct
from pathlib import Path
from zipfile import ZipFile

MOD = 'ender_trigon_unofficial'
PACKAGE = 'nonamecrackers2/endertrigon/'


def verify(archive, minecraft, loader):
    java = {'1.20.1': 17, '1.21.1': 21, '26.1.2': 25}[minecraft]
    checks = 0

    def require(condition, message):
        nonlocal checks
        checks += 1
        if not condition:
            raise ValueError(message)

    with ZipFile(archive) as jar:
        names = set(jar.namelist())
        require(len(names) == len(jar.namelist()), 'Duplicate archive entries')
        documents = {n: json.loads(jar.read(n)) for n in names if n.endswith('.json')}
        require(bool(documents), 'No JSON resources')
        for n in names:
            require('endertrigon:' not in n, 'Old resource namespace: ' + n)
        for legal in ('LICENSE', 'NOTICE'):
            require(any(Path(n).name.startswith(legal) for n in names), 'Missing ' + legal)

        mixin = documents[MOD + '.mixins.json']
        require(mixin['required'] and mixin['injectors']['defaultRequire'] == 1, 'Non-strict Mixin configuration')
        require(mixin['compatibilityLevel'] == 'JAVA_' + str(java), 'Mixin Java level')
        base = mixin['package'].replace('.', '/') + '/'
        client_mixins = {base + n + '.class' for n in mixin['client']}
        for side in ('mixins', 'client'):
            for name in mixin[side]:
                require(base + name + '.class' in names, 'Missing Mixin: ' + name)
        classes = [n for n in names if n.startswith(PACKAGE) and n.endswith('.class')]
        require(len(classes) > 40, 'Missing game classes')
        for name in classes:
            code = jar.read(name)
            require(code[:4] == b'\xca\xfe\xba\xbe' and struct.unpack('>H', code[6:8])[0] == java + 44, 'Wrong Java bytecode: ' + name)
            if loader == 'fabric':
                require(b'net/neoforged/' not in code and b'net/minecraftforge/' not in code, 'Forge API in Fabric class: ' + name)
            if not name.startswith(PACKAGE + 'client/') and name not in client_mixins:
                require(b'net/minecraft/client/' not in code, 'Client Minecraft API in common class: ' + name)

        if loader == 'fabric':
            require('META-INF/neoforge.mods.toml' not in names, 'NeoForge metadata in Fabric jar')
            metadata = documents['fabric.mod.json']
            require(metadata['id'] == MOD and metadata['version'] == '1.0.1', 'Wrong mod identity/version')
            require(metadata['environment'] == '*', 'Wrong environment')
            require(metadata['depends']['minecraft'] == minecraft, 'Minecraft dependency must match this build exactly')
            require(metadata['depends']['java'] == '>=' + str(java), 'Wrong Java dependency')
            require('fabric-api' in metadata['depends'], 'Missing Fabric API dependency')
            for side, entries in metadata['entrypoints'].items():
                for entry in entries:
                    require(entry.replace('.', '/') + '.class' in names, 'Missing ' + side + ' entrypoint')
        else:
            require('fabric.mod.json' not in names, 'Fabric metadata in NeoForge jar')
            metadata = jar.read('META-INF/neoforge.mods.toml').decode()
            require('${' not in metadata, 'Unexpanded NeoForge metadata')
            for line in ('modLoader = "javafml"', 'loaderVersion = "[11,)"', 'modId = "' + MOD + '"', 'version = "1.0.1"', 'versionRange = "[26.1.2]"', 'modId = "neoforge"'):
                require(line in metadata, 'Missing metadata: ' + line)

        # Follow every model, texture and sound owned by this mod; vanilla assets are external.
        def own_reference(identifier, kind, context):
            if not isinstance(identifier, str) or not identifier.startswith(MOD + ':'):
                return
            target = 'assets/' + MOD + '/' + kind + '/' + identifier.split(':', 1)[1]
            target += '.png' if kind == 'textures' else '.json'
            require(target in names, context + ' -> missing ' + target)

        def walk(value, context):
            if isinstance(value, dict):
                for key, child in value.items():
                    if key in ('model', 'parent'):
                        own_reference(child, 'models', context)
                    if key == 'textures' and isinstance(child, dict):
                        for texture in child.values():
                            own_reference(texture, 'textures', context)
                    walk(child, context)
            elif isinstance(value, list):
                for child in value:
                    walk(child, context)

        for name, doc in documents.items():
            if name.startswith('assets/'):
                walk(doc, name)
            require('endertrigon:' not in json.dumps(doc), 'Old resource namespace in ' + name)
        for name, sound in documents['assets/' + MOD + '/sounds.json'].items():
            for entry in sound['sounds']:
                identifier = entry if isinstance(entry, str) else entry['name']
                if identifier.startswith(MOD + ':'):
                    require('assets/' + MOD + '/sounds/' + identifier.split(':', 1)[1] + '.ogg' in names, 'Missing sound: ' + name)

        data = 'data/' + MOD + '/'
        recipe_dir = 'recipes' if minecraft == '1.20.1' else 'recipe'
        advancement_dir = 'advancements' if minecraft == '1.20.1' else 'advancement'
        require(data + recipe_dir + '/dragon_horn.json' in names, 'Missing recipe')
        require(data + advancement_dir + '/recipes/tools/dragon_horn.json' in names, 'Missing advancement')
        wrong = 'recipe' if minecraft == '1.20.1' else 'recipes'
        require(not any(n.startswith(data + wrong + '/') for n in names), 'Wrong recipe directory')
        recipe = documents[data + recipe_dir + '/dragon_horn.json']
        require(recipe['type'] == 'minecraft:crafting_shaped', 'Wrong recipe type')
        require(recipe['result'].get('item' if minecraft == '1.20.1' else 'id') == MOD + ':dragon_horn', 'Wrong recipe result format')
        for ingredient in recipe['key'].values():
            require(isinstance(ingredient, str) if minecraft == '26.1.2' else isinstance(ingredient, dict) and 'item' in ingredient, 'Wrong ingredient format')
        for tag, registry in (('damage_type/is_fire', 'damage_type'), ('damage_type/is_projectile', 'damage_type')):
            for identifier in documents['data/minecraft/tags/' + tag + '.json']['values']:
                if identifier.startswith(MOD + ':'):
                    require(data + registry + '/' + identifier.split(':', 1)[1] + '.json' in names, 'Unresolved registry tag: ' + identifier)
        if minecraft == '26.1.2':
            for item in ('baby_dragon_egg', 'dragon_horn'):
                require('assets/' + MOD + '/items/' + item + '.json' in names, 'Missing modern item definition: ' + item)
            horn = documents['assets/' + MOD + '/items/dragon_horn.json']['model']
            require(horn['property'] == 'minecraft:using_item' and horn['on_true']['model'].endswith('/tooting_dragon_horn'), 'Missing horn use model')
            require('overrides' not in documents['assets/' + MOD + '/models/item/dragon_horn.json'], 'Legacy item override')
            require(data + 'instrument/dragon_horn.json' in names, 'Missing data-driven instrument')
        for item in ('dragon_horn', 'baby_dragon_egg'):
            require('assets/' + MOD + '/models/item/' + item + '.json' in names, 'Missing item model')

    return {'file': Path(archive).name, 'minecraft': minecraft, 'loader': loader, 'java': java, 'classes': len(classes), 'json': len(documents), 'checks': checks, 'sha256': hashlib.sha256(Path(archive).read_bytes()).hexdigest()}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('archive', type=Path)
    parser.add_argument('minecraft', choices=['1.20.1', '1.21.1', '26.1.2'])
    parser.add_argument('loader', choices=['fabric', 'neoforge'])
    parser.add_argument('--report', type=Path)
    args = parser.parse_args()
    report = verify(args.archive, args.minecraft, args.loader)
    output = json.dumps(report, indent=2) + '\n'
    if args.report:
        args.report.write_text(output, encoding='utf-8')
    print(output, end='')
