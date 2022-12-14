package com.zouhair.magicalium_g.fluids;


import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.blocks.InitBlocks;
import com.zouhair.magicalium_g.items.InitItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Magicalium_g.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_STEAM = FLUIDS.register( "steam",
            () -> new ForgeFlowingFluid.Source(InitFluids.STEAM_PROPERTIES));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING_STEAM = FLUIDS.register( "flowing_steam",
            () -> new ForgeFlowingFluid.Flowing(InitFluids.STEAM_PROPERTIES));



    public static final ForgeFlowingFluid.Properties STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            InitFluidTypes.STEAM_FLUID_TYPE, SOURCE_STEAM, FLOWING_STEAM)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(InitBlocks.STEAM_BLOCK).bucket(InitItems.STEAM_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
