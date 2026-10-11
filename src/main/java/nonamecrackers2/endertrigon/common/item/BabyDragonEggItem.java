/* Copyright 2026 lingmu. Licensed under Apache-2.0. */
package nonamecrackers2.endertrigon.common.item;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
public class BabyDragonEggItem extends BlockItem {
    public BabyDragonEggItem(Block block, Properties properties) { super(block, properties.useBlockDescriptionPrefix()); }
    @Override public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> text, TooltipFlag flag) {
        text.accept(Component.translatable("block.ender_trigon_unofficial.baby_dragon_egg.use").withStyle(ChatFormatting.GRAY));
    }
}
