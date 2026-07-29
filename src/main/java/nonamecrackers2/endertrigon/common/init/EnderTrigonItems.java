/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.21.1 unofficial port.

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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.item.DragonHornItem;
import nonamecrackers2.endertrigon.common.tag.EnderTrigonInstrumentTags;

public class EnderTrigonItems
{
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EnderTrigonMod.MODID);
	private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EnderTrigonMod.MODID);
	
	public static final DeferredItem<BlockItem> BABY_DRAGON_EGG = ITEMS.register("baby_dragon_egg", () -> new BlockItem(EnderTrigonBlocks.BABY_DRAGON_EGG.get(), new Item.Properties().rarity(Rarity.EPIC)));
	public static final DeferredItem<DragonHornItem> DRAGON_HORN = ITEMS.register("dragon_horn", () -> new DragonHornItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).fireResistant(), EnderTrigonInstrumentTags.DRAGON_HORN));
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup." + EnderTrigonMod.MODID))
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.icon(() -> DRAGON_HORN.get().getDefaultInstance())
			.displayItems((parameters, output) -> {
				output.accept(BABY_DRAGON_EGG);
				output.accept(DRAGON_HORN);
			})
			.build());
	
	public static void register(IEventBus modBus)
	{
		ITEMS.register(modBus);
		CREATIVE_MODE_TABS.register(modBus);
	}
	
	public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event)
	{
		if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS))
			event.accept(BABY_DRAGON_EGG);
		else if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES))
			event.accept(DRAGON_HORN);
	}
}
