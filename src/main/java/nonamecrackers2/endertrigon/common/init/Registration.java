/* Copyright 2026 lingmu. Licensed under Apache-2.0. */

package nonamecrackers2.endertrigon.common.init;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
/** Registers eagerly during the Fabric initializer; suppliers preserve the common API. */
public final class Registration {
    private Registration() {}
    public static <B, T extends B> Supplier<T> add(Registry<B> registry, ResourceLocation id, T value) {
        Registry.register(registry, id, value);
        return () -> value;
    }
}
