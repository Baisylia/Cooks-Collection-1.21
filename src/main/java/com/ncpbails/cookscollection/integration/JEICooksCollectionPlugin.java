package com.ncpbails.cookscollection.integration;

import com.ncpbails.cookscollection.CooksCollection;
import com.ncpbails.cookscollection.block.ModBlocks;
import com.ncpbails.cookscollection.recipe.OvenRecipe;
//import com.ncpbails.cookscollection.recipe.OvenShapedRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICooksCollectionPlugin implements IModPlugin {
    public static RecipeType<OvenRecipe> BAKING_TYPE =
            new RecipeType<>(OvenRecipeCategory.UID, OvenRecipe.class);

//    public static RecipeType<OvenShapedRecipe> BAKING_SHAPED_TYPE =
//            new RecipeType<>(OvenShapedRecipeCategory.UID, OvenShapedRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return CooksCollection.locate("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper())
//                , new OvenShapedRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        CCRecipes modRecipes = new CCRecipes();

        registration.addRecipes(BAKING_TYPE, modRecipes.getOvenRecipes());

//        List<OvenShapedRecipe> recipesShaped = rm.getAllRecipesFor(OvenShapedRecipe.Type.INSTANCE);
//        registration.addRecipes(BAKING_SHAPED_TYPE, recipesShaped);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        var stack = ModBlocks.OVEN.get().asItem().getDefaultInstance();
//        registration.addRecipeCatalyst(stack, BAKING_SHAPED_TYPE);
        registration.addRecipeCatalyst(stack, BAKING_TYPE);
    }
}
