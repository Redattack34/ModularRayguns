package com.castlebravostudios.rayguns.api

import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.block.Block
import net.minecraft.inventory.InventoryCrafting


case class LensGrinderRecipe( recipe : ShapedRecipes, ticks : Short)
object LensGrinderRecipeRegistry {

  private var recipes = Seq[LensGrinderRecipe]()

  def getRecipe( i : InventoryCrafting ) : Option[LensGrinderRecipe] =
    recipes.find( recipe => recipe.recipe.matches( i, null ) )

  def register( ticks : Short, result : ItemStack, recipe : Any* ) : Unit =
    register( buildShapedRecipe( result, recipe:_* ), ticks )
  def register( recipe : ShapedRecipes, ticks: Short ) : Unit =
    register( LensGrinderRecipe( recipe, ticks ) )
  def register( recipe : LensGrinderRecipe ) : Unit = recipes = recipe +: recipes

  def buildShapedRecipe( result : ItemStack, recipe : Any* ) : ShapedRecipes = {
    val (strings, mappings) = separateRecipe( List( recipe:_* ) )
    val (width : Int, height : Int) = dimensions( strings )
    val map : Map[Char, ItemStack] = parseMappings( mappings )

    val items = Array( strings.mkString.toList.map( c => map.get(c).orNull ):_* )
    new ShapedRecipes( width, height, items, result )
  }

  private def separateRecipe( recipe : List[Any] ) : ( List[String], List[Any] ) = recipe match {
    case ( head : Array[String] ) :: tail  => ( List(head:_*), tail)
    case ( head : String ) :: tail => {
      val (strings, rest) = separateRecipe( tail )
      ( head +: strings, rest )
    }
    case _ => ( List(), recipe )
  }

  private def dimensions( strings : List[String] ) : (Int, Int) =
    ( strings.map(_.length).max, strings.length )

  private def parseMappings( mappings : List[Any] ) : Map[Char, ItemStack] = mappings match {
    case ( c : Char ) :: ( i : Item ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char ) :: ( i : Block ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char ) :: ( i : ItemStack ) :: tail => parseMappings( tail ) + ( c -> i )
    case ( c : Char, i : Item ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char, i : Block ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char, i : ItemStack ) :: tail => parseMappings( tail ) + ( c -> i )
    case _ => Map()
  }
}