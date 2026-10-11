/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.21.1 Fabric unofficial port.

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

package nonamecrackers2.endertrigon.common.init;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import nonamecrackers2.endertrigon.EnderTrigonMod;
public class EnderTrigonSoundEvents {
    public static final Supplier<SoundEvent> BABY_DRAGON_EGG_BREAKS = create("baby_dragon_egg_breaks");
    public static final Supplier<SoundEvent> ENDER_DRAGON_LAYS_EGG = create("dragon_lays_egg");
    public static final Supplier<SoundEvent> DRAGON_HORN = create("dragon_horn");
    private static Supplier<SoundEvent> create(String id) { return Registration.add(BuiltInRegistries.SOUND_EVENT, EnderTrigonMod.id(id), SoundEvent.createVariableRangeEvent(EnderTrigonMod.id(id))); }
    public static void register() {}
}
