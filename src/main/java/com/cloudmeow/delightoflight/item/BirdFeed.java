package com.cloudmeow.delightoflight.item;

import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.utility.DFTags;
import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.registry.DFItems;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.utility.MathUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BirdFeed extends Item {
    public BirdFeed(Properties properties) {
        super(properties);
    }

    public static final List<MobEffectInstance> EFFECTS = Lists.newArrayList(
            new MobEffectInstance(DFEffects.TURBULENCE.get(), 6000, 0));

    @Mod.EventBusSubscriber(modid = DelightoFlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class BirdFeedEvent {
        @SubscribeEvent
        public static void onBirdFeedApplied(PlayerInteractEvent.EntityInteract event){
            Player player = event.getEntity();
            Entity target = event.getTarget();
            ItemStack heldStack = event.getItemStack();
            if (target instanceof LivingEntity entity && target.getType().is(DFTags.BIRD_FEED_USERS)) {
                boolean isTameable = entity instanceof TamableAnimal;
                if (entity.isAlive() && (!isTameable || ((TamableAnimal) entity).isTame()) && heldStack.getItem().equals(DFItems.BIRD_FEED.get())) {
                    entity.setHealth(entity.getMaxHealth());
                    for (MobEffectInstance effect : EFFECTS) {
                        entity.addEffect(new MobEffectInstance(effect));
                    }
                    entity.level().playSound(null, target.blockPosition(), SoundEvents.PARROT_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
                    for (int i = 0; i < 5; ++i) {
                        double d0 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d1 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d2 = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level().addParticle(ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                    }
                    if (!player.isCreative()) {
                        heldStack.shrink(1);
                    }
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            MutableComponent textWhenFeeding = Component.translatable("delighto_flight." + "tooltip.bird_feed.when_feeding", new Object[0]);
            tooltip.add(textWhenFeeding.withStyle(ChatFormatting.GRAY));
            MutableComponent effectDescription;
            MobEffect effect;
            for(Iterator var6 = EFFECTS.iterator(); var6.hasNext(); tooltip.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()))) {
                MobEffectInstance effectInstance = (MobEffectInstance)var6.next();
                effectDescription = Component.literal(" ");
                MutableComponent effectName = Component.translatable(effectInstance.getDescriptionId());
                effectDescription.append(effectName);
                effect = effectInstance.getEffect();
                if (effectInstance.getAmplifier() > 0) {
                    effectDescription.append(" ").append(Component.translatable("potion.potency." + effectInstance.getAmplifier()));
                }
                if (effectInstance.getDuration() > 20) {
                    effectDescription.append(" (").append(MobEffectUtil.formatDuration(effectInstance, 1.0F)).append(")");
                }
            }

        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (target instanceof Parrot parrot) {
            if (parrot.isAlive() && parrot.isTame()) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
