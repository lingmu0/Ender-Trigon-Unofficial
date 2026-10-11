/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.21.1 Fabric unofficial port.

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
import java.util.function.Supplier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.item.DragonHornItem;
import nonamecrackers2.endertrigon.common.tag.EnderTrigonInstrumentTags;
public class EnderTrigonItems {
    public static final Supplier<BlockItem> BABY_DRAGON_EGG = Registration.add(BuiltInRegistries.ITEM, EnderTrigonMod.id("baby_dragon_egg"), new BlockItem(EnderTrigonBlocks.BABY_DRAGON_EGG.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<DragonHornItem> DRAGON_HORN = Registration.add(BuiltInRegistries.ITEM, EnderTrigonMod.id("dragon_horn"), new DragonHornItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).fireResistant(), EnderTrigonInstrumentTags.DRAGON_HORN));
    public static void register() {
        Registration.add(BuiltInRegistries.CREATIVE_MODE_TAB, EnderTrigonMod.id("main"), FabricItemGroup.builder().title(Component.translatable("itemGroup." + EnderTrigonMod.MODID)).icon(() -> DRAGON_HORN.get().getDefaultInstance()).displayItems((parameters, output) -> { output.accept(BABY_DRAGON_EGG.get()); output.accept(DRAGON_HORN.get()); }).build());
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> entries.accept(BABY_DRAGON_EGG.get()));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(DRAGON_HORN.get()));
    }
}
