package com.baisylia.cookscollection.integration.emi;

import com.baisylia.cookscollection.CooksCollection;
import com.baisylia.cookscollection.block.ModBlocks;
import com.baisylia.cookscollection.block.entity.screen.ModMenus;
import com.baisylia.cookscollection.integration.CCRecipes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

@EmiEntrypoint
public class EMIModestMiningPlugin implements EmiPlugin {

    static final ResourceLocation TEXTURE = CooksCollection.locate("textures/gui/oven_gui_jei.png");

    public static final EmiRecipeCategory SHAPELESS_BAKING = new EmiRecipeCategory(CooksCollection.locate("shapeless_baking"), EmiStack.of(ModBlocks.OVEN.get()), simplifiedRenderer(0, 0));
    public static final EmiRecipeCategory SHAPED_BAKING = new EmiRecipeCategory(CooksCollection.locate("shaped_baking"), EmiStack.of(ModBlocks.OVEN.get()), simplifiedRenderer(0, 0));

    private static EmiRenderable simplifiedRenderer(int u, int v) {
        return (draw, x, y, delta) -> {
            draw.blit(TEXTURE, x, y, u, v, 120, 60, 120, 60);
        };
    }

    @Override
    public void register(EmiRegistry registry) {
        var forge = EmiStack.of(ModBlocks.OVEN.get());
        registry.addCategory(SHAPELESS_BAKING);
        registry.addWorkstation(SHAPELESS_BAKING, forge);
        registry.addCategory(SHAPED_BAKING);
        registry.addWorkstation(SHAPED_BAKING, forge);
        CCRecipes ccRecipes = new CCRecipes();
        var access = Minecraft.getInstance().level.registryAccess();
        for (var recipe : ccRecipes.getOvenHolders()) {
            registry.addRecipe(new OvenEmiRecipe(recipe.value(), recipe.id(), access));
        }
        for (var recipe : ccRecipes.getOvenShapedHolders()) {
            registry.addRecipe(new OvenShapedEmiRecipe(recipe.value(), recipe.id(), access));
        }
        registry.addRecipeHandler(ModMenus.OVEN_MENU.get(), new OvenRecipeHandler());
    }
}
