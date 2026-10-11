/*
 * Copyright 2022 nonamecrackers2
 * Modified 2026 by lingmu for the Minecraft 1.21.1 Fabric unofficial port.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nonamecrackers2.endertrigon.common.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nonamecrackers2.endertrigon.common.entity.BabyEnderDragon;

public class DragonHornItem extends InstrumentItem
{
	public DragonHornItem(Item.Properties properties, TagKey<Instrument> instruments)
	{
		super(properties, instruments);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		var result = super.use(level, player, hand);
		if (!level.isClientSide() && result.getResult() == InteractionResult.CONSUME)
		{
			AABB box = player.getBoundingBox().inflate(32.0F);
			var dragons = player.level().getEntitiesOfClass(BabyEnderDragon.class, box, d -> d.distanceTo(player) <= 32.0F);
			int total = dragons.size();
			int totalSitting = 0;
			for (BabyEnderDragon dragon : dragons)
			{
				if (dragon.isOrderedToSit())
					totalSitting++;
			}
			for (BabyEnderDragon dragon : dragons)
			{
				if (player.getUUID().equals(dragon.getOwnerUUID()))
				{
					if ((float)totalSitting / (float)total < 0.5F)
					{
						for (int i = 0; i < 10; i++)
						{
							int x = dragon.getRandom().nextIntBetweenInclusive(-3, 3);
							int y = dragon.getRandom().nextIntBetweenInclusive(-1, 1);
							int z = dragon.getRandom().nextIntBetweenInclusive(-3, 3);
							BlockPos pos = player.blockPosition().offset(x, y, z);
							PathType pathType = WalkNodeEvaluator.getPathTypeStatic(dragon, pos.mutable());
							if (pathType == PathType.WALKABLE)
							{
								if (level.noCollision(dragon, dragon.getLocalBoundsForPose(Pose.SITTING).move(pos)))
								{
									dragon.setPhase(BabyEnderDragon.Phase.LAND);
									dragon.setMoveTarget(Vec3.atBottomCenterOf(pos.above(1)));
								}
							}
						}
					}
					else
					{
						dragon.setPhase(BabyEnderDragon.Phase.CIRCLE);
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> text, TooltipFlag flag)
	{
		text.add(Component.translatable("item.ender_trigon_unofficial.dragon_horn.use").withStyle(ChatFormatting.GRAY));
	}
}
