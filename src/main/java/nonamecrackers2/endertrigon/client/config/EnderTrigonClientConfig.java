/* Copyright 2026 lingmu. Licensed under Apache-2.0. */

package nonamecrackers2.endertrigon.client.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class EnderTrigonClientConfig
{
    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.BooleanValue USE_CUSTOM_DRAGON_MODEL;

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        USE_CUSTOM_DRAGON_MODEL = builder
                .comment("Render the adult Ender Dragon with Trigon's extra heads. Restart the client after changing this.")
                .define("useCustomDragonModel", true);
        SPEC = builder.build();
    }

    private EnderTrigonClientConfig() {}

    public static boolean useCustomDragonModel()
    {
        return SPEC.isLoaded() ? USE_CUSTOM_DRAGON_MODEL.get() : USE_CUSTOM_DRAGON_MODEL.getDefault();
    }
}
