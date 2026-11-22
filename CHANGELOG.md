# 1.0.2 | Minecraft 1.21.1 NeoForge

- Fixed a crash when loading alongside Ender Dragon Plus. Trigon now filters the dragon's contact and knockback targets while Plus can still adjust damage and knockback.
- Release a grabbed player when another attack interrupts the carry phase. Avoid continuing a holding-pattern tick after switching the dragon to another phase.

Validation used the published Ender Dragon Plus 0.3 JAR and NeoForge class files to apply both mods' Mixins for client and server. In-game combat is pending verification.
