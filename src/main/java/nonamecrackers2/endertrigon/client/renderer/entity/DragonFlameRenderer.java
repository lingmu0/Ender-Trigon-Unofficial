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

package nonamecrackers2.endertrigon.client.renderer.entity;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.DragonFlame;
public class DragonFlameRenderer extends EntityRenderer<DragonFlame, EntityRenderState> {
    public DragonFlameRenderer(EntityRendererProvider.Context context) { super(context); }
    @Override public EntityRenderState createRenderState() { return new EntityRenderState(); }
    @Override protected int getBlockLightLevel(DragonFlame entity, BlockPos pos) { return 15; }
    @Override public void submit(EntityRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState camera) {
        stack.pushPose(); stack.scale(2,2,2); stack.mulPose(camera.orientation); stack.mulPose(Axis.YP.rotationDegrees(180));
        var texture = EnderTrigonMod.id("textures/entity/dragon_flame.png");
        collector.submitCustomGeometry(stack, RenderTypes.entityCutout(texture), (pose, consumer) -> quad(consumer, pose, state.lightCoords));
        collector.submitCustomGeometry(stack, RenderTypes.eyes(texture), (pose, consumer) -> quad(consumer, pose, state.lightCoords));
        stack.popPose(); super.submit(state, stack, collector, camera);
    }
    private static void quad(VertexConsumer c, PoseStack.Pose p, int light) {
        vertex(c,p,light,-0.5F,-0.25F,0,1); vertex(c,p,light,0.5F,-0.25F,1,1); vertex(c,p,light,0.5F,0.75F,1,0); vertex(c,p,light,-0.5F,0.75F,0,0);
    }
    private static void vertex(VertexConsumer c, PoseStack.Pose p, int light, float x, float y, float u, float v) {
        c.addVertex(p,x,y,0).setColor(-1).setUv(u,v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(p,0,1,0);
    }
}
