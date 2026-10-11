/* Copyright 2026 lingmu. Licensed under Apache-2.0. */

package nonamecrackers2.endertrigon.client.renderer.entity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
public class BabyDragonRenderState extends LivingEntityRenderState {
    public boolean onGround;
    public float flap;
    public final double[][] history = new double[64][3];
    public double[] getLatency(int offset) { return history[offset & 63]; }
}
