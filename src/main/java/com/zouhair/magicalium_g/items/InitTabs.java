package com.zouhair.magicalium_g.items;

import com.zouhair.magicalium_g.blocks.InitBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class InitTabs {
    public static final CreativeModeTab MAGICALIUM_G_ORE_TAB = new CreativeModeTab("magicalium_g_ores") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(InitBlocks.ANTHRACITE_COAL_ORE.get());
        }
    };

    public static final CreativeModeTab MAGICALIUM_G_MATERIAL_TAB = new CreativeModeTab("magicalium_g_materials") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(InitItems.ANTHRACITE_COAL.get());
        }
    };

    public static final CreativeModeTab MAGICALIUM_G_MACHINE_TAB = new CreativeModeTab("magicalium_g_machines") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(InitBlocks.BRONZE_BOILER.get());
        }
    };

    public static final CreativeModeTab MAGICALIUM_G_TOOL_TAB = new CreativeModeTab("magicalium_g_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(InitItems.IRON_HAMMER.get());
        }
    };
}
