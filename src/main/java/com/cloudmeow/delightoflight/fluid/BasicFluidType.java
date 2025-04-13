package com.cloudmeow.delightoflight.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class BasicFluidType extends FluidType {
    private final int color;
    public BasicFluidType(int color) {
        super(FluidType.Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
        );
        this.color = color;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return color;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return new ResourceLocation("block/water_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return new ResourceLocation("block/water_flow");
            }
        });
    }
}
