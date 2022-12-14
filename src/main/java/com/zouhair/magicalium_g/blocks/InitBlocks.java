package com.zouhair.magicalium_g.blocks;

import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.blocks.custom.BenchBlock;
import com.zouhair.magicalium_g.blocks.custom.BoilerBlock;
import com.zouhair.magicalium_g.fluids.InitFluids;
import com.zouhair.magicalium_g.items.InitItems;
import com.zouhair.magicalium_g.items.InitTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Magicalium_g.MOD_ID);


    public static final RegistryObject<Block> BRONZE_BENCH = registerBlock("bronze_bench",
            () -> new BenchBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_MACHINE_TAB);
    public static final RegistryObject<Block> BRONZE_BOILER = registerBlock("bronze_boiler",
            () -> new BoilerBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_MACHINE_TAB);


    // liquids
    public static final RegistryObject<LiquidBlock> STEAM_BLOCK = BLOCKS.register("steam_block",
            () -> new LiquidBlock(InitFluids.SOURCE_STEAM, BlockBehaviour.Properties.copy(Blocks.WATER)));

    // storage blocks
    public static final RegistryObject<Block> LIGNITE_COAL_BLOCK = registerBlock("lignite_coal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_MATERIAL_TAB);
    public static final RegistryObject<Block> BITUMINOUS_COAL_BLOCK = registerBlock("bituminous_coal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_MATERIAL_TAB);
    public static final RegistryObject<Block> ANTHRACITE_COAL_BLOCK = registerBlock("anthracite_coal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5.0f, 6.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_MATERIAL_TAB);


    // ore blocks
        // stone ores
    public static final RegistryObject<Block> LIGNITE_COAL_ORE = registerBlock("lignite_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> BITUMINOUS_COAL_ORE = registerBlock("bituminous_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> ANTHRACITE_COAL_ORE = registerBlock("anthracite_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> MAGNETITE_ORE = registerBlock("magnetite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> TELLURIC_IRON_ORE = registerBlock("telluric_iron_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> VANADIUM_MAGNETITE_ORE = registerBlock("vanadium_magnetite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> BROWN_LIMONITE_ORE = registerBlock("brown_limonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> YELLOW_LIMONITE_ORE = registerBlock("yellow_limonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> BANDED_IRON_ORE = registerBlock("banded_iron_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
        // deepslate ores
    public static final RegistryObject<Block> DEEPSLATE_LIGNITE_COAL_ORE = registerBlock("deepslate_lignite_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_BITUMINOUS_COAL_ORE = registerBlock("deepslate_bituminous_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_ANTHRACITE_COAL_ORE = registerBlock("deepslate_anthracite_coal_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_MAGNETITE_ORE = registerBlock("deepslate_magnetite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_TELLURIC_IRON_ORE = registerBlock("deepslate_telluric_iron_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_VANADIUM_MAGNETITE_ORE = registerBlock("deepslate_vanadium_magnetite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_BROWN_LIMONITE_ORE = registerBlock("deepslate_brown_limonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_YELLOW_LIMONITE_ORE = registerBlock("deepslate_yellow_limonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);
    public static final RegistryObject<Block> DEEPSLATE_BANDED_IRON_ORE = registerBlock("deepslate_banded_iron_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6.0f, 3.0f)
                    .requiresCorrectToolForDrops()
            ), InitTabs.MAGICALIUM_G_ORE_TAB);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                                CreativeModeTab tab) {
       return InitItems.ITEMS.register(name, () -> new BlockItem(block.get(),
               new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
