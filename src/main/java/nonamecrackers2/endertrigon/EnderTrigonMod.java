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


package nonamecrackers2.endertrigon;


import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import nonamecrackers2.endertrigon.client.init.EnderTrigonRenderers;
import nonamecrackers2.endertrigon.common.config.EnderTrigonConfig;

import nonamecrackers2.endertrigon.common.init.EnderTrigonBlockEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlocks;
import nonamecrackers2.endertrigon.common.init.EnderTrigonDragonPhases;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonInstruments;
import nonamecrackers2.endertrigon.common.init.EnderTrigonItems;
import nonamecrackers2.endertrigon.common.init.EnderTrigonSoundEvents;

@Mod(EnderTrigonMod.MODID)
public class EnderTrigonMod
{
	public static final String MODID = "ender_trigon_unofficial";
	
	public EnderTrigonMod(IEventBus modBus, ModContainer mod)
	{
		EnderTrigonEntityTypes.register(modBus);
		EnderTrigonBlocks.register(modBus);
		EnderTrigonBlockEntityTypes.register(modBus);
		EnderTrigonSoundEvents.register(modBus);
		EnderTrigonItems.register(modBus);
		EnderTrigonInstruments.register(modBus);
		modBus.addListener(this::commonSetup);

		modBus.addListener(EnderTrigonEntityTypes::registerAttributes);
		modBus.addListener(EnderTrigonItems::buildCreativeTabContents);

		if (net.neoforged.fml.loading.FMLEnvironment.getDist() == net.neoforged.api.distmarker.Dist.CLIENT)
            modBus.register(EnderTrigonRenderers.class);
		mod.registerConfig(ModConfig.Type.COMMON, EnderTrigonConfig.COMMON_SPEC);
	}
	
	public void commonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> {
			EnderTrigonDragonPhases.register();
		});
	}
	
	public static Identifier id(String path)
	{
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}
