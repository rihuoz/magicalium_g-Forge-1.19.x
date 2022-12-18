package com.zouhair.magicalium_g.blocks.entity.custom;

import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.blocks.entity.InitBlockEntities;
import com.zouhair.magicalium_g.fluids.InitFluids;
import com.zouhair.magicalium_g.fluids.handler.MGFluidTanks;
import com.zouhair.magicalium_g.items.InitItems;
import com.zouhair.magicalium_g.screens.BoilerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class BoilerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int ISHandler_SIZE = 3;
    public static final int BUCKET_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int ASH_SLOT = 2;
    public static final int WATER_TANK = 0;
    public static final int STEAM_TANK = 1;
    public static final int TANK_CAPACITY = 16000;
    public static final Component TITLE = Component.translatable("container." + Magicalium_g.MOD_ID + ".boiler");
    public final MGFluidTanks.Boiler fluidTankHandler = new MGFluidTanks.Boiler(TANK_CAPACITY, this);
    public final ItemStackHandler itemStackHandler = createItemStackHandler(ISHandler_SIZE);
    public LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();
    public LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 100;
    public BoilerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.BRONZE_BOILER_ENTITY.get(), pPos, pBlockState);
        data = createData();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BoilerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    // Capability
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
         if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
             return lazyItemHandler.cast();
         }
         if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
             return lazyFluidHandler.cast();
         }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidTankHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("boiler_block.progress", this.progress);
        pTag = fluidTankHandler.writeToNBT(pTag);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag);
        progress = pTag.getInt("boiler_block.progress");
        fluidTankHandler.readFromNBT(pTag);
    }

    // CUSTOM methods

    private ItemStackHandler createItemStackHandler(int size) {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return switch (slot) {
                    case BUCKET_SLOT -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
                    case FUEL_SLOT -> stack.getItem() == Items.COAL;
                    case ASH_SLOT -> false;
                    default -> super.isItemValid(slot, stack);
                };
            }
        };
    }
    private ContainerData createData() {
        return new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> BoilerBlockEntity.this.progress;
                    case 1 -> BoilerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> BoilerBlockEntity.this.progress = pValue;
                    case 1 -> BoilerBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void drop() {
        SimpleContainer inventory = new SimpleContainer(ISHandler_SIZE);
        for(int i=0; i<ISHandler_SIZE; i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BoilerBlockEntity blockEntity) {
        if(level.isClientSide()) {
            return;
        }

        if(canBoil(blockEntity)) {
            blockEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(blockEntity.progress >= blockEntity.maxProgress) {
                produceSteam(blockEntity);
                blockEntity.restProgress();
            }
        }

        if (hasBucketInBucketSlot(blockEntity)){
            transferFluidToTank(blockEntity);
        }
    }


    private static void transferFluidToTank(BoilerBlockEntity blockEntity) {
        blockEntity.itemStackHandler.getStackInSlot(BUCKET_SLOT).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
                .ifPresent( handler -> {
                            int drainAmount = Math.min(blockEntity.fluidTankHandler.getTank(WATER_TANK).getSpace(), 1000);
                            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                            if (blockEntity.fluidTankHandler.isFluidValid(WATER_TANK, stack)) {
                                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                                fillTankWithFluid(blockEntity, stack, handler.getContainer());
                            }
                        });
    }

    private static void fillTankWithFluid(BoilerBlockEntity blockEntity, FluidStack stack, ItemStack container) {
        blockEntity.fluidTankHandler.getTank(WATER_TANK).fill(stack, IFluidHandler.FluidAction.EXECUTE);

        blockEntity.itemStackHandler.extractItem(BUCKET_SLOT, 1, false);
        blockEntity.itemStackHandler.insertItem(BUCKET_SLOT, container, false);
    }

    private static boolean hasBucketInBucketSlot(BoilerBlockEntity blockEntity) {
        return blockEntity.itemStackHandler.getStackInSlot(BUCKET_SLOT).getCount() > 0 ;
    }

    private void restProgress() {
        this.progress = 0;
    }

    private static void produceSteam(BoilerBlockEntity blockEntity) {

        blockEntity.fluidTankHandler.getTank(WATER_TANK).drain(100, IFluidHandler.FluidAction.EXECUTE);
        blockEntity.itemStackHandler.extractItem(FUEL_SLOT, 1, false);

        blockEntity.fluidTankHandler.getTank(STEAM_TANK).fill( new FluidStack(InitFluids.SOURCE_STEAM.get(), 100), IFluidHandler.FluidAction.EXECUTE);

        blockEntity.itemStackHandler.setStackInSlot(ASH_SLOT, new ItemStack(InitItems.LIGNITE_COAL.get(),
                    blockEntity.itemStackHandler.getStackInSlot(ASH_SLOT).getCount() + 1));
    }

    private static boolean canBoil(BoilerBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(ISHandler_SIZE);
        for(int i = 0; i< ISHandler_SIZE; i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }


        return hasWaterAmount(blockEntity) && hasFuel(blockEntity) &&
                canInsertAmountIntoAshSlot(inventory, new ItemStack(InitItems.LIGNITE_COAL.get(), 1)) ;

    }

    private static boolean hasFuel(BoilerBlockEntity blockEntity) {
        return blockEntity.itemStackHandler.getStackInSlot(FUEL_SLOT).getItem() == Items.COAL;
    }

    private static boolean hasWaterAmount(BoilerBlockEntity blockEntity) {
        return blockEntity.fluidTankHandler.getTank(WATER_TANK).getFluid().getFluid() == Fluids.WATER
                && blockEntity.fluidTankHandler.getTank(WATER_TANK).getFluid().getAmount() >= 100;
    }

    private static boolean canInsertAmountIntoAshSlot(SimpleContainer inventory, ItemStack itemStack) {
        return ( inventory.getItem(ASH_SLOT).getItem() == itemStack.getItem() ||
                inventory.getItem(ASH_SLOT).isEmpty() ) // checKes if I can't put stack in slot

                && inventory.getItem(ASH_SLOT).getMaxStackSize() > inventory.getItem(ASH_SLOT).getCount();
    }

    public BlockPos getWorldPos() {
        return this.worldPosition;
    }

}
