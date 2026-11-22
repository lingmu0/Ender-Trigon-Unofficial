# Ender Trigon Unofficial

An unofficial port of [Ender Trigon](https://github.com/nonamecrackers2/ender-trigon), originally created by nonamecrackers2. Adds a three-headed Ender Dragon, custom attack phases, dragon flames, hatchable baby dragons and a dragon horn.

| Minecraft | Loader | Java | Branch |
| --- | --- | --- | --- |
| 1.20.1 | Fabric | 17 | [fabric-1.20.1](https://github.com/lingmu0/Ender-Trigon-Unofficial/tree/fabric-1.20.1) |
| 1.21.1 | Fabric | 21 | [fabric-1.21.1](https://github.com/lingmu0/Ender-Trigon-Unofficial/tree/fabric-1.21.1) |
| 1.21.1 | NeoForge | 21 | [main](https://github.com/lingmu0/Ender-Trigon-Unofficial) |
| 26.1.2 | Fabric | 25 | [fabric-26.1.2](https://github.com/lingmu0/Ender-Trigon-Unofficial/tree/fabric-26.1.2) |
| 26.1.2 | NeoForge | 25 | [neoforge-26.1.2](https://github.com/lingmu0/Ender-Trigon-Unofficial/tree/neoforge-26.1.2) |

Install matching jars on both client and server. Fabric builds require [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api); 26.1.2 Fabric requires Loader 0.19.5 or newer. Do not install together with the original Ender Trigon mod.

Downloads: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ender-trigon-unofficial).

## Ender Dragon Plus compatibility

Ender Trigon Unofficial 1.0.2 fixes a crash when loading with Ender Dragon Plus 0.3 on NeoForge 1.21.1 and 26.1.2. Plus remains optional. Its contact damage and knockback hooks can run alongside Trigon's target filters and three-headed dragon. Trigon releases a grabbed player when another attack interrupts its carry phase. [Ender Dragon Plus on CurseForge](https://www.curseforge.com/minecraft/mc-mods/ender-dragon-plus)

## Building and verification

Check out the desired branch and run `gradlew.bat build` (Windows) or `./gradlew build`. NeoForge 1.21.1 uses Java 21 for Gradle. The other branches use Java 25 for Gradle, with Java 17 or 21 toolchains for the older Fabric targets.

The new ports include `verifyMixins` in `build`: it checks the actual Minecraft bytecode for target classes, shadow/accessor/invoker members, overwrite signatures, callback arguments and invocation injection points, without launching Minecraft. Release packaging also checks JSON, datapack paths, item models, Java bytecode versions and production Mixin remapping. Run `python tools/verify_release.py <jar> <minecraft> <fabric|neoforge>` to repeat the resource/metadata checks (Python 3.9+). In-game rendering, AI behavior and multiplayer interaction still need runtime verification; the new ports are published as beta builds.

Fabric settings are generated in `config/ender_trigon_unofficial.json`; NeoForge uses `config/ender_trigon_unofficial-common.toml`. Restart after changing phase toggles, and use matching settings on client and server so phase IDs agree.

## License and attribution

Licensed under Apache-2.0. Original code, textures and sounds are by nonamecrackers2. The upstream Java package is retained; this port uses the `ender_trigon_unofficial` mod/resource namespace. See [LICENSE](LICENSE) and [NOTICE](NOTICE).
