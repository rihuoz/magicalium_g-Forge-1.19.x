package com.zouhair.magicalium_g.items;

import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.fluids.InitFluidTypes;
import com.zouhair.magicalium_g.fluids.InitFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Magicalium_g.MOD_ID);

    // tools
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new PickaxeItem(InitTiers.TIER_1, 1, 1.0f,
                    new Item.Properties().tab(InitTabs.MAGICALIUM_G_TOOL_TAB)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new PickaxeItem(InitTiers.TIER_2, 2, 1.0f,
                    new Item.Properties().tab(InitTabs.MAGICALIUM_G_TOOL_TAB)));

    // buckets
    public static final RegistryObject<Item> STEAM_BUCKET = ITEMS.register("steam_bucket",
            () -> new BucketItem(InitFluids.SOURCE_STEAM,
                    new Item.Properties().stacksTo(1).tab(InitTabs.MAGICALIUM_G_MATERIAL_TAB)
                            .craftRemainder(Items.BUCKET)));

    // materials
    public static final RegistryObject<Item> LIGNITE_COAL = ITEMS.register("lignite_coal",
            () -> new Item(new Item.Properties().tab(InitTabs.MAGICALIUM_G_MATERIAL_TAB)));

    public static final RegistryObject<Item> BITUMINOUS_COAL = ITEMS.register("bituminous_coal",
            () -> new Item(new Item.Properties().tab(InitTabs.MAGICALIUM_G_MATERIAL_TAB)));

    public static final RegistryObject<Item> ANTHRACITE_COAL = ITEMS.register("anthracite_coal",
            () -> new Item(new Item.Properties().tab(InitTabs.MAGICALIUM_G_MATERIAL_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
