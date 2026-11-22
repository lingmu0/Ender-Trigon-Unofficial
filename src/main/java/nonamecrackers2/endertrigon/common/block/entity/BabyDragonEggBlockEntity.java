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

package nonamecrackers2.endertrigon.common.block.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import nonamecrackers2.endertrigon.EnderTrigonMod;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;
import nonamecrackers2.endertrigon.common.init.EnderTrigonBlockEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonEntityTypes;
import nonamecrackers2.endertrigon.common.init.EnderTrigonSoundEvents;

public class BabyDragonEggBlockEntity extends BlockEntity
{
	private int timeTillSpawn;
	private @Nullable UUID hatcher;
	
	public BabyDragonEggBlockEntity(BlockPos pos, BlockState state)
	{
		super(EnderTrigonBlockEntityTypes.BABY_DRAGON_EGG.get(), pos, state);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, BabyDragonEggBlockEntity egg)
	{
		if (!(level instanceof ServerLevel serverLevel) || egg.timeTillSpawn <= 0 || --egg.timeTillSpawn > 0)
			return;

		BabyEnderDragon dragon = EnderTrigonEntityTypes.BABY_ENDER_DRAGON.get()
				.create(serverLevel, null, pos, EntitySpawnReason.TRIGGERED, true, false);
		if (dragon == null)
		{
			egg.retryHatching();
			return;
		}

		UUID owner = egg.hatcher;
		if (owner != null)
		{
			dragon.setOwnerUUID(owner);
			dragon.setTame(true, true);
			dragon.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(EnderTrigonMod.id("tamed_health"),
					16.0D, AttributeModifier.Operation.ADD_VALUE));
			dragon.setHealth(dragon.getMaxHealth());
			dragon.setPersistenceRequired();
		}

		if (!serverLevel.addFreshEntity(dragon))
		{
			egg.retryHatching();
			return;
		}

		if (owner != null && serverLevel.getEntity(owner) instanceof ServerPlayer player)
			CriteriaTriggers.TAME_ANIMAL.trigger(player, dragon);

		dragon.playSound(SoundEvents.ENDER_DRAGON_GROWL, 3.0F, dragon.getVoicePitch());
		serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.05);
		serverLevel.playSound(null, pos, EnderTrigonSoundEvents.BABY_DRAGON_EGG_BREAKS.get(), SoundSource.BLOCKS, 5.0F, 1.0F);
		serverLevel.removeBlock(pos, false);
		serverLevel.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
	}

	public void beginHatching(RandomSource random, @Nullable UUID player)
	{
		boolean changed = false;
		if (this.hatcher == null && player != null)
		{
			this.hatcher = player;
			changed = true;
		}
		if (this.timeTillSpawn <= 0)
		{
			this.timeTillSpawn = 30 + random.nextInt(20);
			changed = true;
		}
		if (changed)
			this.setChanged();
	}

	private void retryHatching()
	{
		this.timeTillSpawn = 20;
		this.setChanged();
	}

	@Override
	protected void saveAdditional(ValueOutput output)
	{
		super.saveAdditional(output);
		output.putInt("TimeTillSpawn", this.timeTillSpawn);
		output.storeNullable("Hatcher", UUIDUtil.CODEC, this.hatcher);
	}

	@Override
	protected void loadAdditional(ValueInput input)
	{
		super.loadAdditional(input);
		this.timeTillSpawn = input.getIntOr("TimeTillSpawn", 0);
		this.hatcher = input.read("Hatcher", UUIDUtil.CODEC).orElse(null);
	}
}
