/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.20.1 Fabric unofficial port.

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

package nonamecrackers2.endertrigon.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.boss.EnderDragonPart;

@Mixin(DragonFireball.class)
public abstract class MixinDragonFireball extends AbstractHurtingProjectile
{
	private MixinDragonFireball(EntityType<? extends AbstractHurtingProjectile> type, Level level)
	{
		super(type, level);
	}
	
	@Override
	protected boolean ownedBy(Entity entity)
	{
		UUID owner = null;
		if (this.getOwner() != null)
			owner = this.getOwner().getUUID();
		return super.ownedBy(entity) || (entity instanceof EnderDragonPart part && part.parentMob.getUUID().equals(owner));
	}
}
