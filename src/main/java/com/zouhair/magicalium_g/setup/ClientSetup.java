package com.zouhair.magicalium_g.setup;

import com.zouhair.magicalium_g.blocks.InitBlocks;
import com.zouhair.magicalium_g.fluids.InitFluids;
import com.zouhair.magicalium_g.screens.BoilerScreen;
import com.zouhair.magicalium_g.screens.InitMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        // ItemBlockRenderTypes.setRenderLayer(InitBlocks.BRONZE_BENCH.get(), RenderType.cutout());

        setRenderLayerTranslucent();

        MenuScreens.register(InitMenuTypes.BOILER_MENU.get(), BoilerScreen::new);
    }

    private static void setRenderLayerTranslucent() {
        ItemBlockRenderTypes.setRenderLayer(InitFluids.SOURCE_STEAM.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitFluids.FLOWING_STEAM.get(), RenderType.translucent());

        /*
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.ANTHRACITE_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.BITUMINOUS_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.LIGNITE_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.MAGNETITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.TELLURIC_IRON_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.VANADIUM_MAGNETITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.BROWN_LIMONITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.YELLOW_LIMONITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.BANDED_IRON_ORE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_ANTHRACITE_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_BITUMINOUS_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_LIGNITE_COAL_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_MAGNETITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_TELLURIC_IRON_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_VANADIUM_MAGNETITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_BROWN_LIMONITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_YELLOW_LIMONITE_ORE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.DEEPSLATE_BANDED_IRON_ORE.get(), RenderType.translucent());

         */
    }
}
