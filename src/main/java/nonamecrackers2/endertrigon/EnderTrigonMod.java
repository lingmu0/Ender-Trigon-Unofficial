/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.20.1 Fabric unofficial port.

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
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import nonamecrackers2.endertrigon.common.config.EnderTrigonConfig;
import nonamecrackers2.endertrigon.common.init.*;
public class EnderTrigonMod implements ModInitializer {
    public static final String MODID = "ender_trigon_unofficial";
    @Override public void onInitialize() {
        EnderTrigonConfig.load();
        EnderTrigonEntityTypes.register();
        EnderTrigonBlocks.register();
        EnderTrigonBlockEntityTypes.register();
        EnderTrigonSoundEvents.register();
        EnderTrigonInstruments.register();
        EnderTrigonItems.register();
        EnderTrigonDragonPhases.register();
    }
    public static ResourceLocation id(String path) { return new ResourceLocation(MODID, path); }
}
