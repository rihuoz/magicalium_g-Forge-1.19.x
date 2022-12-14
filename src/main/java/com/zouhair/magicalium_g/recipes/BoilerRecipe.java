package com.zouhair.magicalium_g.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zouhair.magicalium_g.Magicalium_g;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoilerRecipe  implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int INPUT_SLOT = 0;
    private final int FUEL_SLOT = 1;
    private final int OUTPUT_SLOT = 2;

    public BoilerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    //  used to check whether the recipe can be used
    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        return recipeItems.get(INPUT_SLOT).test(pContainer.getItem(INPUT_SLOT));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BoilerRecipe> {
        private Type () { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "boiling";
    }

    public static class Serializer implements RecipeSerializer<BoilerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Magicalium_g.MOD_ID, "boiling");

        // 	This decodes a Recipe from JSON
        @Override
        public @NotNull BoilerRecipe fromJson(@NotNull ResourceLocation pRecipeId, JsonObject json) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            // FluidStack fluid = FluidJSONUtil.readFluid(json.get("fluid").getAsJsonObject());

            for (int i=0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new BoilerRecipe(pRecipeId, output, inputs);
        }

        // 	This encodes a Recipe on the server to send to the client
        @Nullable
        @Override
        public BoilerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i=0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new BoilerRecipe(pRecipeId, output, inputs);
        }

        // 	This decodes a Recipe on the client
        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BoilerRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(), false);

        }

    }
}
