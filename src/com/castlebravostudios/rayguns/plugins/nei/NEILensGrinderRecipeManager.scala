package com.castlebravostudios.rayguns.plugins.nei

import codechicken.nei.recipe.ShapedRecipeHandler
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import codechicken.nei.NEIClientUtils
import codechicken.nei.NEIServerUtils
import com.castlebravostudios.rayguns.api.LensGrinderRecipe

class NEILensGrinderRecipeManager extends ShapedRecipeHandler {

  override def loadCraftingRecipes( result : ItemStack ) : Unit = {
    for {
      recipe <- LensGrinderRecipeRegistry.recipes
      if ( NEIServerUtils.areStacksSameTypeCrafting( recipe.recipe.getRecipeOutput(), result ) )
    } this.arecipes.add( getShape( recipe ) )
  }

  override def loadCraftingRecipes( outputId : String, results : Object* ) : Unit = {
    if ( outputId == "crafting" ) {
      for {
        recipe <- LensGrinderRecipeRegistry.recipes
      } this.arecipes.add( getShape( recipe ))
    }
    else super.loadCraftingRecipes( outputId, results:_* )
  }

  override def loadUsageRecipes( ingredient : ItemStack ) : Unit = {
    for {
      recipe <- LensGrinderRecipeRegistry.recipes
      if ( recipe.recipe.recipeItems.exists( stack =>
        NEIServerUtils.areStacksSameTypeCrafting( stack, ingredient ) ) )
    } this.arecipes.add( getShape( recipe ) )
  }

  override def getGuiClass() = classOf[LensGrinderGui]
  override def getGuiTexture() = "rayguns:textures/gui/container/lens_grinder.png"
  override def getRecipeName = "Lens Grinder"

  def getShape(recipe: LensGrinderRecipe) : CachedShapedRecipe = {
    new CachedShapedRecipe( recipe.recipe )
  }
}