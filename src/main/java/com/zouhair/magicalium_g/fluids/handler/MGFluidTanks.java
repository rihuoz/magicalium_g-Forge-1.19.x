package com.zouhair.magicalium_g.fluids.handler;

import com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity;
import com.zouhair.magicalium_g.networking.InitMessages;
import com.zouhair.magicalium_g.networking.packets.BoilerFluidSyncS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class MGFluidTanks{

    public static class Boiler extends FluidTank {
        private final int capacity = this.getCapacity();
        private final int WATER_TANK = 0;
        private final int STEAM_TANK = 1;
        private final BoilerBlockEntity blockEntity;
        private final FluidTank WaterTank = new FluidTank(capacity);
        private final FluidTank SteamTank = new FluidTank(capacity);


        public Boiler(int capacity, BoilerBlockEntity blockEntity) {
            super(capacity);
            this.blockEntity = blockEntity;
        }

        public Boiler(int capacity, Predicate<FluidStack> validator, BoilerBlockEntity blockEntity) {
            super(capacity, validator);
            this.blockEntity = blockEntity;
        }

        @Override
        protected void onContentsChanged() {
            blockEntity.setChanged();
            if(!blockEntity.getLevel().isClientSide) {
                InitMessages.sendToClients(new BoilerFluidSyncS2CPacket(
                        blockEntity.fluidTankHandler.getFluidInTank(WATER_TANK),
                        blockEntity.fluidTankHandler.getFluidInTank(STEAM_TANK),
                        blockEntity.getWorldPos()));
            }
        }

        @Override
        public int getTanks() {
            return 2;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return switch (tank) {
                case WATER_TANK -> WaterTank.getFluid();
                case STEAM_TANK -> SteamTank.getFluid();
                default -> super.getFluidInTank(tank);
            };
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return switch (tank) {
                case WATER_TANK -> stack.getFluid() == Fluids.WATER;
                case STEAM_TANK -> false;
                default -> super.isFluidValid(tank, stack);
            };
        }

        public FluidTank getTank(int tank) {
            return switch (tank) {
                case WATER_TANK -> this.WaterTank;
                case STEAM_TANK -> this.SteamTank;
                default -> throw new IllegalStateException("Can't get tank: " + tank + ", must be less then 2");
            };
        }

        public void setFluidInTank(int tank, FluidStack fluidStack) {
            switch (tank) {
                case WATER_TANK -> WaterTank.setFluid(fluidStack);
                case STEAM_TANK -> SteamTank.setFluid(fluidStack);
                default -> throw new IllegalStateException("Can't set fluid in tank: " + tank + ", must be less then 2");
            }
        }
    }

}
