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

package nonamecrackers2.endertrigon.common.config;

import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import net.neoforged.neoforge.common.ModConfigSpec;
import nonamecrackers2.endertrigon.common.init.EnderTrigonDragonPhases;

public class EnderTrigonConfig
{
	public static final Set<String> CANNOT_BE_TOGGLED = Set.of("CarryPlayer", "ChargeUp");
	public static final CommonConfig COMMON;
	public static final ModConfigSpec COMMON_SPEC;
	
	static
	{
		var commonPar = new ModConfigSpec.Builder().configure(CommonConfig::new);
		COMMON = commonPar.getLeft();
		COMMON_SPEC = commonPar.getRight();
	}
	
	public static class CommonConfig
	{
		public final Map<String, BooleanSupplier> enabledCustomDragonPhases;
		public final ModConfigSpec.ConfigValue<Boolean> skipLandingPhase;
		public final ModConfigSpec.ConfigValue<Boolean> crashPhaseDestroysBlocks;
		public final ModConfigSpec.ConfigValue<Integer> maxBabyEnderDragons;
		public final ModConfigSpec.ConfigValue<Integer> attacksUntilPerch;
		
		public CommonConfig(ModConfigSpec.Builder builder)
		{
			builder.comment("Enabled custom Ender Dragon phases").push("enabled_custom_phases");
			
			this.enabledCustomDragonPhases = EnderTrigonDragonPhases.CUSTOM_DRAGON_PHASES.entrySet().stream().map(e -> 
			{
				BooleanSupplier supplier;
				if (CANNOT_BE_TOGGLED.contains(e.getKey()))
				{
					supplier = () -> true;
				}
				else
				{
					var config = builder
							.comment("Specifies if this custom dragon phase should be enabled")
							.define("enable" + e.getKey(), true);
					supplier = config::get;
				}
				return Map.<String, BooleanSupplier>entry(e.getKey(), supplier);
			}).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
			
			builder.pop();
			
			this.skipLandingPhase = builder
					.comment("Whether the landing phase should be skipped more often to shorten long flying periods")
					.define("skipLandingPhase", true);
			this.crashPhaseDestroysBlocks = builder
					.comment("Whether the crash phase should fling destroyed blocks")
					.define("crashPhaseDestroysBlocks", true);
			this.maxBabyEnderDragons = builder
					.comment("Maximum baby Ender Dragons spawned during the hatching phase")
					.defineInRange("maxBabyEnderDragons", 4, 1, 16);
			this.attacksUntilPerch = builder
					.comment("Special attacks plus remaining end crystals required before the dragon may perch")
					.defineInRange("attacksUntilPerch", 4, 1, 16);
		}
	}
}
