/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.plugins.nei

import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui

import codechicken.nei.NEIServerUtils

import codechicken.nei.recipe.ShapedRecipeHandler
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect
import net.minecraft.item.ItemStack

class NEILensGrinderRecipeManager extends ShapedRecipeHandler {

  override def loadCraftingRecipes( result : ItemStack ) : Unit = {
    for {
      recipe <- LensGrinderRecipeRegistry.recipes
      if ( NEIServerUtils.areStacksSameTypeCrafting( recipe.recipe.getRecipeOutput(), result ) )
    } this.arecipes.add( getShape( recipe ) )
  }

  override def loadCraftingRecipes( outputId : String, results : Object* ) : Unit = {
    if ( outputId == "LensGrinder" ) {
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

  override def loadTransferRects() : Unit = {
    transferRects.add( new RecipeTransferRect(
        new java.awt.Rectangle(84, 23, 24, 18),
        NEIModularRaygunsConfig.recipeKey) );
  }

  override def drawExtras( recipeIndex: Int ) : Unit = {
    drawProgressBar(84, 24, 176, 0, 24, 16, 48, 0);
  }

  override def getGuiClass() : Class[LensGrinderGui] =
    classOf[LensGrinderGui]

  override def getGuiTexture() : String =
    "rayguns:textures/gui/container/lens_grinder.png"

  override def getRecipeName : String =
    "Lens Grinder"

  override def getOverlayIdentifier() : String =
    NEIModularRaygunsConfig.recipeKey

  private def getShape(recipe: LensGrinderRecipe) : CachedShapedRecipe = {
    new CachedShapedRecipe( recipe.recipe )
  }
}