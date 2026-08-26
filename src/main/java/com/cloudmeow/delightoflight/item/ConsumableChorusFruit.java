package com.cloudmeow.delightoflight.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class ConsumableChorusFruit extends ConsumableItem {
    public ConsumableChorusFruit(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entityLiving);
        if (!level.isClientSide) {
            double d0 = entityLiving.getX();
            double d1 = entityLiving.getY();
            double d2 = entityLiving.getZ();

            for(int i = 0; i < 16; ++i) {
                double d3 = entityLiving.getX() + (entityLiving.getRandom().nextDouble() - 0.5D) * 16.0D;
                double d4 = Mth.clamp(entityLiving.getY() + (double)(entityLiving.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
                double d5 = entityLiving.getZ() + (entityLiving.getRandom().nextDouble() - 0.5D) * 16.0D;
                if (entityLiving.isPassenger()) {
                    entityLiving.stopRiding();
                }

                Vec3 vec3 = entityLiving.position();
                level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entityLiving));
                net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(entityLiving, d3, d4, d5);
                if (event.isCanceled()) return itemstack;
                if (entityLiving.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    SoundEvent soundevent = entityLiving instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entityLiving.playSound(soundevent, 1.0F, 1.0F);
                    break;
                }
            }

            if (entityLiving instanceof Player) {
                ((Player)entityLiving).getCooldowns().addCooldown(this, 20);
            }
        }

        return itemstack;
    }
}
