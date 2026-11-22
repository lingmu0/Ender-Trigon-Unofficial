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

package nonamecrackers2.endertrigon.mixin;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.dragon.EnderDragonModel;
import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
import nonamecrackers2.endertrigon.client.renderer.entity.DragonRendererAdditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonModel.class)
public class MixinEnderDragonModel
{
    @Unique private ModelPart[] endertrigon$heads;
    @Unique private ModelPart[][] endertrigon$necks;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void endertrigon$findExtraParts(ModelPart root, CallbackInfo callback)
    {
        ModelPart[] heads = new ModelPart[2];
        ModelPart[][] necks = new ModelPart[2][5];
        for (int headIndex = 0; headIndex < heads.length; headIndex++)
        {
            String headName = "trigon_head" + headIndex;
            if (!root.hasChild(headName))
                return;
            heads[headIndex] = root.getChild(headName);
            if (!heads[headIndex].hasChild("jaw"))
                return;
            for (int segment = 0; segment < necks[headIndex].length; segment++)
            {
                String neckName = "trigon_neck" + headIndex + "_" + segment;
                if (!root.hasChild(neckName))
                    return;
                necks[headIndex][segment] = root.getChild(neckName);
            }
        }
        this.endertrigon$heads = heads;
        this.endertrigon$necks = necks;
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;)V", at = @At("TAIL"))
    private void endertrigon$animateExtraHeads(EnderDragonRenderState state, CallbackInfo callback)
    {
        if (this.endertrigon$heads != null)
            DragonRendererAdditions.animate(state, this.endertrigon$heads, this.endertrigon$necks);
    }
}
