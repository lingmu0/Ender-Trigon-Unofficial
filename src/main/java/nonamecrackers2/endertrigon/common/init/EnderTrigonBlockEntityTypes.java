/*
 * Copyright 2022 nonamecrackers2

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

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.block.entity.BabyDragonEggBlockEntity;

public class EnderTrigonBlockEntityTypes
{
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EnderTrigonMod.MODID);
	
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BabyDragonEggBlockEntity>> BABY_DRAGON_EGG = BLOCK_ENTITY_TYPES.register("baby_dragon_egg", () ->
			BlockEntityType.Builder.of(BabyDragonEggBlockEntity::new, EnderTrigonBlocks.BABY_DRAGON_EGG.get()).build(null));
	
	public static void register(IEventBus modBus)
	{
		BLOCK_ENTITY_TYPES.register(modBus);
	}
	}
