package com.zouhair.magicalium_g.setup;

import com.zouhair.magicalium_g.networking.InitMessages;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            InitMessages.register();

        });

    }
}
