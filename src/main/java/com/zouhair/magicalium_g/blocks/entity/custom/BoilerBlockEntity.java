package com.zouhair.magicalium_g.blocks.entity.custom;

import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.blocks.entity.InitBlockEntities;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class BoilerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int ISHandler_SIZE = 3;
    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final Component TITLE = Component.translatable("container." + Magicalium_g.MOD_ID + ".boiler");

    public final ItemStackHandler itemStackHandler = createItemStackHandler(ISHandler_SIZE);
    public LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();
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
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? lazyItemHandler.cast() :
                super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("boiler_block.progress", this.progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag);
        progress = pTag.getInt("boiler_block.progress");
    }

    // CUSTOM methods
    private ItemStackHandler createItemStackHandler(int size) {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
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

        if(hasRecipe(blockEntity)) {
            blockEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(blockEntity.progress >= blockEntity.maxProgress) {
                craftItem(blockEntity);
                blockEntity.restProgress();
            }
        }
    }

    private void restProgress() {
        this.progress = 0;
    }

    private static void craftItem(BoilerBlockEntity blockEntity) {
        if(hasRecipe(blockEntity)){
            blockEntity.itemStackHandler.extractItem(INPUT_SLOT, 1, false);
            blockEntity.itemStackHandler.extractItem(FUEL_SLOT, 1, false);

            blockEntity.itemStackHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(InitItems.LIGNITE_COAL.get(),
                    blockEntity.itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + 1));
        }
    }

    private static boolean hasRecipe(BoilerBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(ISHandler_SIZE);
        for(int i = 0; i< ISHandler_SIZE; i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }


        return hasInput(blockEntity) && hasFuel(blockEntity) &&
                canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(InitItems.LIGNITE_COAL.get(), 1));

    }

    private static boolean hasFuel(BoilerBlockEntity blockEntity) {
        return blockEntity.itemStackHandler.getStackInSlot(FUEL_SLOT).getItem() ==
                InitItems.BITUMINOUS_COAL.get();
    }

    private static boolean hasInput(BoilerBlockEntity blockEntity) {
        return blockEntity.itemStackHandler.getStackInSlot(INPUT_SLOT).getItem() ==
                InitItems.ANTHRACITE_COAL.get();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(OUTPUT_SLOT).getItem() == itemStack.getItem() ||
                inventory.getItem(OUTPUT_SLOT).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
            return inventory.getItem(OUTPUT_SLOT).getMaxStackSize() >
                    inventory.getItem(OUTPUT_SLOT).getCount();
    }
}
