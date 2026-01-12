package com.cloudmeow.delightoflight.compat.jade;

import com.cloudmeow.delightoflight.block.CloudBerryBushBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class DFJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DFCropProcess.INSTANCE, CloudBerryBushBlock.class);
    }
}
