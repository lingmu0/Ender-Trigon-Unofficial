/* Copyright 2026 lingmu. Licensed under Apache-2.0. */

package nonamecrackers2.endertrigon.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.model.geom.builders.*;
@Mixin(LayerDefinition.class)
public interface LayerDefinitionAccessor { @Accessor("mesh") MeshDefinition endertrigon$mesh(); }
