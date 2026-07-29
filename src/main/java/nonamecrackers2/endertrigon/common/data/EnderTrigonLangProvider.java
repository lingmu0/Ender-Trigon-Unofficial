/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.21.1 unofficial port.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nonamecrackers2.endertrigon.common.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlocks;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonItems;

public class EnderTrigonLangProvider extends LanguageProvider
{
	public EnderTrigonLangProvider(PackOutput output)
	{
		super(output, EnderTrigonMod.MODID, "en_us");
	}
	
	@Override
	protected void addTranslations()
	{
		this.add("itemGroup." + EnderTrigonMod.MODID, "Ender Trigon Unofficial");
		this.add(EnderTrigonEntityTypes.BABY_ENDER_DRAGON.get(), "Baby Ender Dragon");
		this.add(EnderTrigonEntityTypes.DRAGON_FLAME.get(), "Dragon Flame");
		this.add(EnderTrigonBlocks.BABY_DRAGON_EGG.get(), "Baby Dragon Egg");
		this.add(EnderTrigonItems.DRAGON_HORN.get(), "Dragon Horn");
		this.add("block.ender_trigon_unofficial.baby_dragon_egg.use", "Place down to spawn a tamed, baby Ender Dragon");
		this.add("item.ender_trigon_unofficial.dragon_horn.use", "Use to call tamed baby Ender Dragons");
		this.add("death.attack.dragonFlame", "%1$s was incinerated by %2$s");
	  	this.add("death.attack.dragonFlame.item", "%1$s was incinerated by %2$s using %3$s");
	  	this.add("ender_trigon_unofficial.subtitle.baby_dragon_egg_breaks", "Baby Dragon Egg Breaks");
	  	this.add("ender_trigon_unofficial.subtitle.dragon_lays_egg", "Ender Dragon Lays Egg");
	  	this.add("ender_trigon_unofficial.subtitle.dragon_horn", "Dragon horn plays");
	  	this.add("instrument.ender_trigon_unofficial.dragon_horn", "Dragon Calling");
	}
}
