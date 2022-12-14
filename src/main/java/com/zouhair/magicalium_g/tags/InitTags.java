package com.zouhair.magicalium_g.tags;


import com.zouhair.magicalium_g.Magicalium_g;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class InitTags {

    public static class Blocks {

        // here
        public static final TagKey<Block> ORES_IN_GROUND_STONE =
                forgeTag("ores_in_ground/stone");

        public static final TagKey<Block> ORES_IN_GROUND_DEEPSLATE =
                forgeTag("ores_in_ground/deepslate");

       public static TagKey<Block> tag(String name) {
           return BlockTags.create(new ResourceLocation(Magicalium_g.MOD_ID, name));
       }
       public static TagKey<Block> forgeTag(String name) {
           return BlockTags.create(new ResourceLocation("forge", name));
       }
    }

    public static class Items {

        public static final TagKey<Item> ACCEPTABLE_FUELS_IN_BOILER =
                tag("acceptable_fuels_in_boiler");

        public static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Magicalium_g.MOD_ID, name));
        }
        public static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
