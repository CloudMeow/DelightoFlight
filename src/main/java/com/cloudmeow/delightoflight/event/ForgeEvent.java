package com.cloudmeow.delightoflight.event;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.block.CottonCandyMachineBlock;
import com.cloudmeow.delightoflight.network.DFNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DelightoFlight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvent {
    private static boolean wasRightClickDown = false;
    private static BlockPos targetedMachinePos = null;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.ClientTickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        if (!mc.player.getMainHandItem().is(Items.STICK)) {
            if (targetedMachinePos != null) {
                DFNetworking.INSTANCE.sendToServer(new DFNetworking.StopSpinningMessage(targetedMachinePos));
                targetedMachinePos = null;
                wasRightClickDown = false;
            }
            return;
        }

        boolean isRightClickDown = mc.options.keyUse.isDown();

        if (isRightClickDown && !wasRightClickDown) {
            HitResult hit = mc.hitResult;
            if (hit instanceof BlockHitResult bhr) {
                BlockPos pos = bhr.getBlockPos();
                if (mc.level.getBlockState(pos).getBlock() instanceof CottonCandyMachineBlock) {
                    targetedMachinePos = pos;
                    DFNetworking.INSTANCE.sendToServer(new DFNetworking.StartSpinningMessage(pos));
                }
            }
        }

        if (!isRightClickDown && wasRightClickDown) {
            if (targetedMachinePos != null) {
                DFNetworking.INSTANCE.sendToServer(new DFNetworking.StopSpinningMessage(targetedMachinePos));
                targetedMachinePos = null;
            }
        }

        if (isRightClickDown && targetedMachinePos != null) {
            HitResult hit = mc.hitResult;
            if (!(hit instanceof BlockHitResult bhr) || !bhr.getBlockPos().equals(targetedMachinePos)) {
                DFNetworking.INSTANCE.sendToServer(new DFNetworking.StopSpinningMessage(targetedMachinePos));
                targetedMachinePos = null;
            }
        }

        wasRightClickDown = isRightClickDown;
    }
}
