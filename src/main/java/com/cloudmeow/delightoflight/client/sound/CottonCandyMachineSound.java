package com.cloudmeow.delightoflight.client.sound;

import com.cloudmeow.delightoflight.block.entity.CottonCandyMachineBlockEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CottonCandyMachineSound extends AbstractTickableSoundInstance {
    private final CottonCandyMachineBlockEntity machine;

    public CottonCandyMachineSound(CottonCandyMachineBlockEntity machine, SoundEvent sound, Vec3 pos) {
        super(sound, SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.machine = machine;
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.pitch = 1.0F;
        this.attenuation = Attenuation.LINEAR;
    }

    @Override
    public void tick() {
        if (machine == null || machine.isRemoved()) {
            this.stop();
            return;
        }

        if (machine.isSpinning()) {
            this.volume = Math.min(1.0F, this.volume + 0.05F);
        } else {
            this.volume = Math.max(0.0F, this.volume - 0.05F);
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}
