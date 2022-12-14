package com.zouhair.magicalium_g.recipes;

import com.zouhair.magicalium_g.Magicalium_g;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Magicalium_g.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BoilerRecipe>> BOILING_SERIALIZER =
            SERIALIZERS.register("boiling", () -> BoilerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
