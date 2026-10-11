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
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.*;
public class EnderTrigonEntityTypes {
    public static final Supplier<EntityType<DragonFlame>> DRAGON_FLAME = Registration.add(BuiltInRegistries.ENTITY_TYPE, EnderTrigonMod.id("dragon_flame"), EntityType.Builder.<DragonFlame>of(DragonFlame::new, MobCategory.MISC).sized(1.2F, 1.2F).clientTrackingRange(4).updateInterval(10).fireImmune().build(EnderTrigonMod.id("dragon_flame").toString()));
    public static final Supplier<EntityType<BabyEnderDragon>> BABY_ENDER_DRAGON = Registration.add(BuiltInRegistries.ENTITY_TYPE, EnderTrigonMod.id("baby_ender_dragon"), EntityType.Builder.<BabyEnderDragon>of(BabyEnderDragon::new, MobCategory.MONSTER).sized(1.8F, 0.4F).clientTrackingRange(8).fireImmune().build(EnderTrigonMod.id("baby_ender_dragon").toString()));
    public static void register() { FabricDefaultAttributeRegistry.register(BABY_ENDER_DRAGON.get(), BabyEnderDragon.createAttributes()); }
}
