package com.zouhair.magicalium_g.blocks.entity;

import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.blocks.InitBlocks;
import com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Magicalium_g.MOD_ID);

    public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BRONZE_BOILER_ENTITY =
            BLOCK_ENTITIES.register("bronze_boiler_entity",
                    () -> BlockEntityType.Builder.of(BoilerBlockEntity::new,
                            InitBlocks.BRONZE_BOILER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
