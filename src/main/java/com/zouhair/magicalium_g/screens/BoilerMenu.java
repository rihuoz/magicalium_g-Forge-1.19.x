package com.zouhair.magicalium_g.screens;

import com.zouhair.magicalium_g.blocks.InitBlocks;
import com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity;
import com.zouhair.magicalium_g.screens.slot.ResultSlot;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import static com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity.*;

public class BoilerMenu extends AbstractContainerMenu {
    private final BoilerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack water;
    private FluidStack steam;

    public BoilerMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(pContainerId, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(2));
    }

    public BoilerMenu(int pContainerId, Inventory inventory, BlockEntity entity, ContainerData data ) {
        super(InitMenuTypes.BOILER_MENU.get(), pContainerId);

        checkContainerSize(inventory, 3);
        blockEntity = (BoilerBlockEntity) entity;
        this.level = inventory.player.level;
        this.data = data;
        this.water = blockEntity.fluidTankHandler.getFluidInTank(WATER_TANK);
        this.steam = blockEntity.fluidTankHandler.getFluidInTank(STEAM_TANK);

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
            // to add check if the item in slot BUCKET_SLOT has the FLUID_ITEM_HANDLER_CAP
            this.addSlot(new SlotItemHandler(itemHandler, BUCKET_SLOT, 25, 18));
            this.addSlot(new SlotItemHandler(itemHandler, FUEL_SLOT, 134, 53));

            this.addSlot(new ResultSlot(itemHandler, ASH_SLOT, 134, 18));
        });
        addDataSlots(data);

    }

    public void setFluidInTank(int tank, FluidStack fluidStack) {
        if (tank == WATER_TANK)
            this.water = fluidStack;
        if (tank == STEAM_TANK)
            this.steam = fluidStack;
    }

    public FluidStack getFluidStackInTank(int tank) {
        if (tank == WATER_TANK)
            return this.water;
        if (tank == STEAM_TANK)
            return this.steam;
        return FluidStack.EMPTY;
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, InitBlocks.BRONZE_BOILER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    public BlockEntity getBlockEntity() {
        return this.blockEntity;
    }

}
