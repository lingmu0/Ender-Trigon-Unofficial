/* Copyright 2026 lingmu. Licensed under Apache-2.0. */

package nonamecrackers2.endertrigon.client.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import org.slf4j.Logger;

public final class EnderTrigonClientConfig
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String KEY = "useCustomDragonModel";
    private static final boolean USE_CUSTOM_DRAGON_MODEL = readPreference();

    private EnderTrigonClientConfig() {}

    public static boolean useCustomDragonModel()
    {
        return USE_CUSTOM_DRAGON_MODEL;
    }

    private static boolean readPreference()
    {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(EnderTrigonMod.MODID + "-client.json");
        try
        {
            if (Files.notExists(path))
            {
                Files.createDirectories(path.getParent());
                Files.writeString(path, "{\n  \"" + KEY + "\": true\n}\n", StandardOpenOption.CREATE_NEW);
                return true;
            }

            JsonElement value = JsonParser.parseString(Files.readString(path)).getAsJsonObject().get(KEY);
            if (value == null)
                return true;
            if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean())
                throw new JsonSyntaxException(KEY + " must be true or false");
            return value.getAsBoolean();
        }
        catch (IOException | IllegalStateException | JsonSyntaxException e)
        {
            LOGGER.warn("Could not read {}. Using the default Ender Dragon model preference.", path, e);
            return true;
        }
    }
}
