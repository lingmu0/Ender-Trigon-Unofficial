# 1.0.3 | Minecraft 1.21.1 NeoForge

- Added the client option `useCustomDragonModel`. Turn it off to render the vanilla adult Ender Dragon.
- Placed Baby Dragon Eggs hatch for their placer. Right-clicking an unclaimed egg claims it without teleporting or restarting its hatch countdown. A falling egg carries its hatch data.
- If the baby dragon cannot spawn, the egg stays in place and retries. The egg is removed after the dragon successfully joins the world.

[Tamed hatching report](https://github.com/lingmu0/Ender-Trigon-Unofficial/issues/1) · [Hatching crash report](https://github.com/lingmu0/Ender-Trigon-Unofficial/issues/3)

# 1.0.2 | Minecraft 1.21.1 NeoForge

- Fixed a crash when loading alongside Ender Dragon Plus. Trigon now filters the dragon's contact and knockback targets while Plus can still adjust damage and knockback.
- Release a grabbed player when another attack interrupts the carry phase. Avoid continuing a holding-pattern tick after switching the dragon to another phase.

Validation used the published Ender Dragon Plus 0.3 JAR and NeoForge class files to apply both mods' Mixins for client and server. In-game combat is pending verification.
