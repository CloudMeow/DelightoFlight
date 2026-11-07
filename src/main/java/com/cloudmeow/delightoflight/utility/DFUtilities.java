package com.cloudmeow.delightoflight.utility;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.network.payload.HeartAnimationPayload;
import com.cloudmeow.delightoflight.registry.DFEffects;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;

public class DFUtilities {
    public static final ResourceLocation STARDROP_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(DelightoFlight.MOD_ID, "stardrop_health");

    public static boolean checkChefHatExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DFItems.MAGIC_CHEF_HAT.get();
    }

    public static boolean checkCookBookExist(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == DFItems.COOK_BOOK.get();
    }

    public static boolean hasGrindHerbs(LivingEntity entity) {
        return entity.hasEffect(DFEffects.GRIND_HERBS);
    }

    public static boolean getConnectedBlock(BlockGetter blockGetter, BlockPos pos, Block block, Direction direction, Block needBlock, int howLong) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        BlockState blockstate;

        for(int x = 1; x < howLong; ++x) {
            blockpos$mutableblockpos.move(direction);
            blockstate = blockGetter.getBlockState(blockpos$mutableblockpos);
            if(blockstate.is(needBlock)){
                return true;
            } else if(!blockstate.is(block)){
                return false;
            }
        }
        return false;
    }

    public static boolean isSameBlock(BlockGetter blockGetter, BlockPos pos, Block block, Direction direction, int howLong) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();

        BlockState blockstate;

        for(int x = 1; x < howLong; ++x) {
            blockpos$mutableblockpos.move(direction);
            blockstate = blockGetter.getBlockState(blockpos$mutableblockpos);
            if(!blockstate.is(block)){
                return false;
            }
        }
        return true;
    }

    public static boolean chefDelightLoad() {
        return ModList.get().isLoaded("chefsdelight");
    }

    public static boolean twilightDelightLoad() {
        return ModList.get().isLoaded("twilightdelight");
    }

    public static boolean goetyDelightLoad() {
        return ModList.get().isLoaded("goetydelight");
    }

    public static boolean dungeonsDelightLoad() {
        return ModList.get().isLoaded("dungeonsdelight");
    }

    public static boolean thirstLoad() {
        return ModList.get().isLoaded("thirst");
    }

    public static boolean isFullMoon(ServerLevel world) {
        return world.isNight() && world.getMoonPhase() == 0;
    }

    public static boolean isConductive(LivingEntity owner) {
        Level level = owner.level();
        BlockPos pos = owner.blockPosition();
        FluidState fluidState = level.getFluidState(pos);
        return level.isRainingAt(pos) || fluidState.getType().isSame(Fluids.WATER);
    }

    public static VoxelShape rotateShape(VoxelShape shape, Rotation rotation) {
        if (rotation == Rotation.NONE) return shape;

        VoxelShape result = Shapes.empty();
        for (AABB aabb : shape.toAabbs()) {
            double x1 = aabb.minX, y1 = aabb.minY, z1 = aabb.minZ;
            double x2 = aabb.maxX, y2 = aabb.maxY, z2 = aabb.maxZ;
            AABB rotated;
            switch (rotation) {
                case CLOCKWISE_90 -> rotated = new AABB(1 - z2, y1, x1, 1 - z1, y2, x2);
                case CLOCKWISE_180 -> rotated = new AABB(1 - x2, y1, 1 - z2, 1 - x1, y2, 1 - z1);
                case COUNTERCLOCKWISE_90 -> rotated = new AABB(z1, y1, 1 - x2, z2, y2, 1 - x1);
                default -> rotated = aabb;
            }
            result = Shapes.or(result, Block.box(
                    rotated.minX * 16, rotated.minY * 16, rotated.minZ * 16,
                    rotated.maxX * 16, rotated.maxY * 16, rotated.maxZ * 16
            ));
        }
        return result;
    }

    public static void playHeartAnimation(ItemStack itemStack, Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new HeartAnimationPayload(itemStack.copy()));
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (entity == minecraft.player) {
            minecraft.gameRenderer.displayItemActivation(itemStack);
        }
    }
}
