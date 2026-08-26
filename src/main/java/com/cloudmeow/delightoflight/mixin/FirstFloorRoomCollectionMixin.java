package com.cloudmeow.delightoflight.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WoodlandMansionPieces.FirstFloorRoomCollection.class)
public class FirstFloorRoomCollectionMixin {
    @Unique
    private boolean delightoFlight$hasKitchen = false;

    @Inject(method = "get1x1", at = @At("RETURN"), cancellable = true)
    private void get1x1(RandomSource randomSource, CallbackInfoReturnable<String> cir){
        if (!delightoFlight$hasKitchen) {
            cir.setReturnValue("1x1_a6");
            delightoFlight$hasKitchen = true;
        } else {
            int var10000 = randomSource.nextInt(5);
            cir.setReturnValue("1x1_a" + (var10000 + 1));
        }
    }
}
