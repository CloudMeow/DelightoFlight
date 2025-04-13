package com.cloudmeow.delightoflight.datagen;

import com.cloudmeow.delightoflight.DelightoFlight;
import com.cloudmeow.delightoflight.registry.DFItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class DFItemModelProvider extends ItemModelProvider {
    public DFItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DelightoFlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(DFItems.CLOUD_SILK);
        simpleItem(DFItems.PHANTOM_WING);
        simpleItem(DFItems.BIRD_FEED);
        simpleItem(DFItems.ORLEANS_PHANTOM_WING);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DelightoFlight.MOD_ID,"item/" + item.getId().getPath()));
    }
}
