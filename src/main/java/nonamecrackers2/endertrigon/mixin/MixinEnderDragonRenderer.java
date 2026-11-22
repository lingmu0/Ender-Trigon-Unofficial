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

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.monster.dragon.EnderDragonModel;
import nonamecrackers2.endertrigon.client.config.EnderTrigonClientConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonModel.class)
public class MixinEnderDragonRenderer
{
    @Inject(method = "createBodyLayer", at = @At("RETURN"), require = 1)
    private static void endertrigon$addHeads(CallbackInfoReturnable<LayerDefinition> callback)
    {
        if (!EnderTrigonClientConfig.useCustomDragonModel())
            return;

        LayerDefinition layer = callback.getReturnValue();
        PartDefinition root = ((LayerDefinitionAccessor) (Object) layer).endertrigon$mesh().getRoot();
        for (int headIndex = 0; headIndex < 2; headIndex++)
        {
            CubeListBuilder skull = CubeListBuilder.create()
                    .addBox("upperlip", -6, -1, -24, 12, 5, 16, 176, 44)
                    .addBox("upperhead", -8, -8, -10, 16, 16, 16, 112, 30)
                    .addBox("scale", -5, -12, -4, 2, 4, 6, 0, 0)
                    .addBox("scale", 3, -12, -4, 2, 4, 6, 0, 0)
                    .addBox("nostril", -5, -3, -22, 2, 2, 4, 112, 0)
                    .addBox("nostril", 3, -3, -22, 2, 2, 4, 112, 0);
            PartDefinition head = root.addOrReplaceChild("trigon_head" + headIndex, skull, PartPose.ZERO);
            head.addOrReplaceChild("jaw",
                    CubeListBuilder.create().addBox("jaw", -6, 0, -16, 12, 4, 16, 176, 65),
                    PartPose.offset(0, 4, -8));

            CubeListBuilder neck = CubeListBuilder.create()
                    .addBox("box", -5, -5, -5, 10, 10, 10, 192, 104)
                    .addBox("scale", -1, -9, -3, 2, 4, 6, 48, 0);
            for (int segment = 0; segment < 5; segment++)
                root.addOrReplaceChild("trigon_neck" + headIndex + "_" + segment, neck, PartPose.ZERO);
        }
    }
}
