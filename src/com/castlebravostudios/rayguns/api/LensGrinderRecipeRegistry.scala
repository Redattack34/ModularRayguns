package com.castlebravostudios.rayguns.api

import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.block.Block
import net.minecraft.inventory.InventoryCrafting
import com.castlebravostudios.rayguns.utils.ScalaShapedRecipeFactory


case class LensGrinderRecipe( recipe : ShapedRecipes, ticks : Short)
object LensGrinderRecipeRegistry {

  private var recipes = Seq[LensGrinderRecipe]()

  def getRecipe( i : InventoryCrafting ) : Option[LensGrinderRecipe] =
    recipes.find( recipe => recipe.recipe.matches( i, null ) )

  def register( ticks : Short, result : ItemStack, recipe : Any* ) : Unit =
    register( ScalaShapedRecipeFactory( result, recipe:_* ), ticks )
  def register( recipe : ShapedRecipes, ticks: Short ) : Unit =
    register( LensGrinderRecipe( recipe, ticks ) )
  def register( recipe : LensGrinderRecipe ) : Unit = recipes = recipe +: recipes
}