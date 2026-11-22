# 1.0.3 | Minecraft 26.1.2 Fabric

- Added the client option `useCustomDragonModel`. Turn it off to render the vanilla adult Ender Dragon.
- Placed Baby Dragon Eggs hatch for their placer. Right-clicking an unclaimed egg claims it without teleporting or restarting its hatch countdown. A falling egg carries its hatch data.
- If the baby dragon cannot spawn, the egg stays in place and retries. The egg is removed after the dragon successfully joins the world.

[Tamed hatching report](https://github.com/lingmu0/Ender-Trigon-Unofficial/issues/1) · [Hatching crash report](https://github.com/lingmu0/Ender-Trigon-Unofficial/issues/3)

# 1.0.1 — Minecraft 26.1.2 Fabric

- Port the three-headed dragon, custom combat phases, dragon flame, baby dragon egg/companion and dragon horn to 26.1.2 Fabric.
- Adapt loader registration, configuration, resources and version-specific Minecraft APIs.
- Include static Mixin bytecode verification in the build.

Validation: compilation, packaging, resource checks and Mixin bytecode checks. Minecraft was not launched; in-game and multiplayer behavior remain unverified. This is a beta port.
