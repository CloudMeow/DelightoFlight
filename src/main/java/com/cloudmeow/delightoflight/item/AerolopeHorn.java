package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.registry.DFSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class AerolopeHorn extends Item {
    private final int weatherState;

    public AerolopeHorn(Properties properties, int weatherState) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (weatherState == 0) {
            level.playSound(player, player, DFSounds.CLEAR_HORN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        if (weatherState == 1) {
            level.playSound(player, player, DFSounds.RAINY_HORN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        if (weatherState == 2) {
            level.playSound(player, player, DFSounds.THUNDER_HORN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack p_220131_) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            if (weatherState == 0) {
                ((ServerLevel)level).setWeatherParameters(12000, 0, false, false);
            }
            if (weatherState == 1) {
                ((ServerLevel)level).setWeatherParameters(0, 12000, true, false);
            }
            if (weatherState == 2) {
                ((ServerLevel)level).setWeatherParameters(0, 12000, true, true);
            }
        }
        itemStack.shrink(1);
        return itemStack;
    }
}
