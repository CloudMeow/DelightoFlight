package com.cloudmeow.delightoflight.client;

import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import com.cloudmeow.delightoflight.client.sound.CottonCandyMachineSound;
import com.cloudmeow.delightoflight.registry.DFSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public final class CottonCandyMachineClientTicker {
    private CottonCandyMachineClientTicker() {
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, CottonCandyMachineBlockEntity be) {
        if (be.isSpinning()) {
            double cx = pos.getX() + 0.5D;
            double cy = pos.getY() + 0.6875D;
            double cz = pos.getZ() + 0.5D;
            long time = level.getGameTime();
            int color = be.getCurrentColor();
            DustParticleOptions particleData = new DustParticleOptions(new Vector3f(
                    ((color >> 16) & 0xFF) / 255.0F,
                    ((color >> 8) & 0xFF) / 255.0F,
                    (color & 0xFF) / 255.0F), 1.0F);
            int count = 2;
            for (int i = 0; i < count; i++) {
                double t = (double) i / count;
                double angle = time * 0.2D + t * (Math.PI * 2.0D);
                double radius = 0.4D;
                double x = cx + Math.cos(angle) * radius;
                double z = cz + Math.sin(angle) * radius;
                double y = cy + 0.02D + t * 0.3D;
                double tangent = 0.05D;
                level.addParticle(particleData, x, y, z, -Math.sin(angle) * tangent, 0.03D, Math.cos(angle) * tangent);
            }
            if (!be.isPlaying()) {
                Minecraft.getInstance().getSoundManager().play(
                        new CottonCandyMachineSound(be, DFSounds.COTTON_CANDY_MACHINE_WORK.get(), pos.getCenter()));
                be.setPlaying(true);
            }
        } else {
            be.setPlaying(false);
        }
    }
}
