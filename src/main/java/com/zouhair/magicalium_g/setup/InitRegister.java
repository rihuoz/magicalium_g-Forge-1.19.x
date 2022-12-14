package com.zouhair.magicalium_g.setup;

import com.zouhair.magicalium_g.blocks.InitBlocks;
import com.zouhair.magicalium_g.blocks.entity.InitBlockEntities;
import com.zouhair.magicalium_g.fluids.InitFluidTypes;
import com.zouhair.magicalium_g.fluids.InitFluids;
import com.zouhair.magicalium_g.items.InitItems;
import com.zouhair.magicalium_g.recipes.InitRecipes;
import com.zouhair.magicalium_g.screens.InitMenuTypes;
import net.minecraftforge.eventbus.api.IEventBus;

public class InitRegister {
    public static void init(IEventBus eventBus) {

        InitItems.register(eventBus);
        InitBlocks.register(eventBus);

        InitBlockEntities.register(eventBus);
        InitMenuTypes.register(eventBus);

        InitFluids.register(eventBus);
        InitFluidTypes.register(eventBus);

        InitRecipes.register(eventBus);
    }
}
