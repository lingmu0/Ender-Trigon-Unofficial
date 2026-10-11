/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 26.1.2 Fabric unofficial port.

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

package nonamecrackers2.endertrigon.client.init;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.client.renderer.entity.*;
import nonamecrackers2.endertrigon.client.renderer.entity.model.BabyEnderDragonModel;
import nonamecrackers2.endertrigon.common.init.*;
public class EnderTrigonRenderers implements ClientModInitializer {
    @Override public void onInitializeClient() {
        EntityRendererRegistry.register(EnderTrigonEntityTypes.DRAGON_FLAME.get(), DragonFlameRenderer::new);
        EntityRendererRegistry.register(EnderTrigonEntityTypes.BABY_ENDER_DRAGON.get(), BabyEnderDragonRenderer::new);
        ModelLayerRegistry.registerModelLayer(BabyEnderDragonModel.LAYER_LOCATION, BabyEnderDragonModel::createBodyLayer);
    }
}
