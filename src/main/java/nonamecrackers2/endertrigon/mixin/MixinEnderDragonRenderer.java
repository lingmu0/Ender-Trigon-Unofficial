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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.model.monster.dragon.EnderDragonModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
@Mixin(EnderDragonModel.class)
public class MixinEnderDragonRenderer {
    @Inject(method="createBodyLayer", at=@At("RETURN"))
    private static void addHeads(CallbackInfoReturnable<LayerDefinition> cir) {
        var root = ((LayerDefinitionAccessor)(Object)cir.getReturnValue()).endertrigon$mesh().getRoot();
        for (int j=0; j<2; j++) {
            var head = root.addOrReplaceChild("trigon_head"+j, CubeListBuilder.create().addBox("upperlip",-6,-1,-24,12,5,16,176,44).addBox("upperhead",-8,-8,-10,16,16,16,112,30).addBox("scale",-5,-12,-4,2,4,6,0,0).addBox("scale",3,-12,-4,2,4,6,0,0).addBox("nostril",-5,-3,-22,2,2,4,112,0).addBox("nostril",3,-3,-22,2,2,4,112,0), PartPose.ZERO);
            head.addOrReplaceChild("jaw",CubeListBuilder.create().addBox("jaw",-6,0,-16,12,4,16,176,65),PartPose.offset(0,4,-8));
            for(int i=0;i<5;i++) root.addOrReplaceChild("trigon_neck"+j+"_"+i,CubeListBuilder.create().addBox("box",-5,-5,-5,10,10,10,192,104).addBox("scale",-1,-9,-3,2,4,6,48,0),PartPose.ZERO);
        }
    }
}
