package com.zouhair.magicalium_g.networking.packets;

import com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity;
import com.zouhair.magicalium_g.screens.BoilerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity.STEAM_TANK;
import static com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity.WATER_TANK;

public class BoilerFluidSyncS2CPacket {
    private final FluidStack water;
    private final FluidStack steam;
    private final BlockPos bPos;

    public BoilerFluidSyncS2CPacket(FluidStack water, FluidStack steam, BlockPos bPos) {
        this.water = water;
        this.steam = steam;
        this.bPos = bPos;
    }

    public BoilerFluidSyncS2CPacket(FriendlyByteBuf buffer) {
        this.water = buffer.readFluidStack();
        this.steam = buffer.readFluidStack();
        this.bPos = buffer.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeFluidStack(water);
        buffer.writeFluidStack(steam);
        buffer.writeBlockPos(bPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!!
            if (Minecraft.getInstance().level.getBlockEntity(bPos) instanceof BoilerBlockEntity blockEntity) {
                blockEntity.fluidTankHandler.setFluidInTank(WATER_TANK, this.water);
                blockEntity.fluidTankHandler.setFluidInTank(STEAM_TANK, this.steam);

                if (Minecraft.getInstance().player.containerMenu instanceof BoilerMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(bPos)) {
                    menu.setFluidInTank(WATER_TANK, this.water);
                    menu.setFluidInTank(STEAM_TANK, this.steam);
                }
            }
        });
        return true;
    }
}
