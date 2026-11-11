package com.cloudmeow.delightoflight.client;

import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import com.cloudmeow.delightoflight.client.sound.CottonCandyMachineSound;
import com.cloudmeow.delightoflight.registry.DFSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class CottonCandyMachineClientTicker {
    private static final Map<BlockPos, CottonCandyMachineSound> SOUNDS = new HashMap<>();
    private static Level lastLevel;

    private CottonCandyMachineClientTicker() {
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, CottonCandyMachineBlockEntity be) {
        if (level != lastLevel) {
            SOUNDS.values().forEach(sound -> Minecraft.getInstance().getSoundManager().stop(sound));
            SOUNDS.clear();
            lastLevel = level;
        }

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
            CottonCandyMachineSound sound = SOUNDS.get(pos);
            if (sound == null || sound.isStopped()) {
                sound = new CottonCandyMachineSound(be, DFSounds.COTTON_CANDY_MACHINE_WORK.get(), pos.getCenter());
                SOUNDS.put(pos, sound);
                Minecraft.getInstance().getSoundManager().play(sound);
            }
        } else {
            CottonCandyMachineSound sound = SOUNDS.remove(pos);
            if (sound != null) {
                Minecraft.getInstance().getSoundManager().stop(sound);
            }
        }
    }
}
