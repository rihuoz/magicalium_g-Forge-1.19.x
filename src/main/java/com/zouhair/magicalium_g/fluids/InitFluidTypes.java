package com.zouhair.magicalium_g.fluids;

import com.mojang.math.Vector3f;
import com.zouhair.magicalium_g.Magicalium_g;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation STEAM_OVERLAY_RL = new ResourceLocation(Magicalium_g.MOD_ID, "fluid/steam");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Magicalium_g.MOD_ID);


    public static final RegistryObject<FluidType> STEAM_FLUID_TYPE = FLUID_TYPES.register("steam_type",
            () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, STEAM_OVERLAY_RL,
            0xA1FFFFFF, new Vector3f(1f, 1f, 1f),
                   FluidType.Properties.create().viscosity(164).density(600).temperature(100)));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
