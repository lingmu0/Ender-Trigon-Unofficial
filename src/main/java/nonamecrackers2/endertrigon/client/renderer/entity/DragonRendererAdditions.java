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
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
import net.minecraft.util.Mth;
public class DragonRendererAdditions {
    public static void animate(EnderDragonRenderState state, ModelPart[] heads, ModelPart[][] necks) {
        float flap=state.flapTime*(float)(Math.PI*2), rad=(float)(Math.PI/180);
        for(int j=0;j<2;j++) {
            int offset=j+1; float yOffset=j==0?-30:30;
            var start=state.getHistoricalPos(6+offset);
            float rot=Mth.wrapDegrees(state.getHistoricalPos(5+offset).yRot()+(Mth.wrapDegrees(state.getHistoricalPos(5+offset).yRot()-state.getHistoricalPos(10).yRot()))/2);
            float x=0,y=20,z=-12;
            for(int i=0;i<5;i++) {
                var point=state.getHistoricalPos(5+offset-i); var n=necks[j][i];
                n.yRot=Mth.wrapDegrees(point.yRot()-start.yRot()+yOffset)*rad*1.5F;
                n.xRot=Mth.cos(i*0.45F+flap)*0.15F+(state.getHeadPartYOffset(i,start,point)+5)*rad*1.5F*3;
                n.zRot=-Mth.wrapDegrees(point.yRot()-rot)*rad*1.5F;
                n.setPos(x,y,z);
                y+=Mth.sin(n.xRot)*10; z-=Mth.cos(n.yRot)*Mth.cos(n.xRot)*10; x-=Mth.sin(n.yRot)*Mth.cos(n.xRot)*10;
            }
            var head=heads[j]; var point=state.getHistoricalPos(offset); head.setPos(x,y,z);
            head.yRot=Mth.wrapDegrees(point.yRot()-start.yRot())*rad;
            head.xRot=Mth.wrapDegrees(state.getHeadPartYOffset(6,start,point))*rad*1.5F*5;
            head.zRot=-Mth.wrapDegrees(point.yRot()-rot)*rad;
            head.getChild("jaw").xRot=(Mth.sin(flap+400*(j+1))+1)*0.2F;
        }
    }
}
