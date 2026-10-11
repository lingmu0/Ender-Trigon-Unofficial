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

package nonamecrackers2.endertrigon.common.config;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import nonamecrackers2.endertrigon.common.init.EnderTrigonDragonPhases;
/** Fabric configuration is read once, before phase IDs are assigned. Restart after editing. */
public class EnderTrigonConfig {
    public static final Set<String> CANNOT_BE_TOGGLED = Set.of("CarryPlayer", "ChargeUp");
    public static CommonConfig COMMON;
    public record Value<T>(T get) { public T get() { return get; } }
    public static void load() {
        var path = FabricLoader.getInstance().getConfigDir().resolve("ender_trigon_unofficial.json");
        try {
            JsonObject json = Files.exists(path) ? JsonParser.parseString(Files.readString(path)).getAsJsonObject() : new JsonObject();
            COMMON = new CommonConfig(json);
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) Files.writeString(path, new GsonBuilder().setPrettyPrinting().create().toJson(json) + "\n");
        } catch (IOException | RuntimeException e) { throw new IllegalStateException("Cannot load Ender Trigon configuration: " + path, e); }
    }
    private static boolean bool(JsonObject json, String key, boolean fallback) {
        if (!json.has(key)) json.addProperty(key, fallback);
        return json.get(key).getAsBoolean();
    }
    private static int integer(JsonObject json, String key, int fallback) {
        if (!json.has(key)) json.addProperty(key, fallback);
        int value = json.get(key).getAsInt();
        if (value < 1 || value > 16) throw new IllegalArgumentException(key + " must be between 1 and 16");
        return value;
    }
    public static class CommonConfig {
        public final Map<String, BooleanSupplier> enabledCustomDragonPhases;
        public final Value<Boolean> skipLandingPhase, crashPhaseDestroysBlocks;
        public final Value<Integer> maxBabyEnderDragons, attacksUntilPerch;
        CommonConfig(JsonObject json) {
            JsonObject phases = json.has("enabled_custom_phases") ? json.getAsJsonObject("enabled_custom_phases") : new JsonObject();
            json.add("enabled_custom_phases", phases);
            enabledCustomDragonPhases = EnderTrigonDragonPhases.CUSTOM_DRAGON_PHASES.keySet().stream().collect(Collectors.toUnmodifiableMap(key -> key, key -> {
                boolean enabled = CANNOT_BE_TOGGLED.contains(key) || bool(phases, "enable" + key, true);
                return (BooleanSupplier) () -> enabled;
            }));
            skipLandingPhase = new Value<>(bool(json, "skipLandingPhase", true));
            crashPhaseDestroysBlocks = new Value<>(bool(json, "crashPhaseDestroysBlocks", true));
            maxBabyEnderDragons = new Value<>(integer(json, "maxBabyEnderDragons", 4));
            attacksUntilPerch = new Value<>(integer(json, "attacksUntilPerch", 4));
        }
    }
}
