package com.cloudmeow.delightoflight.Loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ReplaceItemLootModifier extends LootModifier {
    public static final Supplier<MapCodec<ReplaceItemLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(
                            inst.group(
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("removed_item").forGetter((m) -> m.removedItem),
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("added_item").forGetter((m) -> m.addedItem),
                                    Codec.INT.optionalFieldOf("min", 0).forGetter((m) -> m.minCount),
                                    Codec.INT.optionalFieldOf("max", 1).forGetter((m) -> m.maxCount)
                            )
                    )
                    .apply(inst, ReplaceItemLootModifier::new)));

    private final Item removedItem;
    private final Item addedItem;
    private final int minCount;
    private final int maxCount;

    protected ReplaceItemLootModifier(LootItemCondition[] conditions, Item removedItem, Item addedItem, int minCount, int maxCount) {
        super(conditions);
        this.removedItem = removedItem;
        this.addedItem = addedItem;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        ItemStack addedStack = new ItemStack(addedItem, lootContext.getRandom().nextInt(maxCount - minCount + 1) + minCount);

        generatedLoot.forEach((item) -> {
            if (item.is(removedItem)) {
                generatedLoot.remove(item);
            }
        });

        if (addedStack.getCount() < addedStack.getMaxStackSize()) {
            generatedLoot.add(addedStack);
        } else {
            int i = addedStack.getCount();

            while (i > 0) {
                ItemStack subStack = addedStack.copy();
                subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                i -= subStack.getCount();
                generatedLoot.add(subStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
