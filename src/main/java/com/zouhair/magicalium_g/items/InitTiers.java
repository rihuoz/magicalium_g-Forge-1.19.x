package com.zouhair.magicalium_g.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;


public class InitTiers {
    public static final ForgeTier TIER_1 = new ForgeTier(1, 600, 1.0f,
            1.0f, 5, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));

    public static final ForgeTier TIER_2 = new ForgeTier(2, 1000, 1.2f,
            1.2f, 12, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.IRON_INGOT));
}
