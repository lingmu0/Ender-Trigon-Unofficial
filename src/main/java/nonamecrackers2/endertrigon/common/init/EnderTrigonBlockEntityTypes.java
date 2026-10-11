/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 26.1.2 NeoForge unofficial port.

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

package nonamecrackers2.endertrigon.common.init;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.block.entity.BabyDragonEggBlockEntity;
public class EnderTrigonBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EnderTrigonMod.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BabyDragonEggBlockEntity>> BABY_DRAGON_EGG = TYPES.register("baby_dragon_egg", () -> new BlockEntityType<>(BabyDragonEggBlockEntity::new, Set.of(EnderTrigonBlocks.BABY_DRAGON_EGG.get())));
    public static void register(IEventBus bus) { TYPES.register(bus); }
}
