package com.castlebravostudios.rayguns.utils

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.Item
import net.minecraft.block.Block
import com.castlebravostudios.rayguns.api.items.RaygunModule

/**
 * A factory for ShapedRecipes which handles Scala varargs better than the default.
 * Also can understand recipes where tuples are used to map between characters and
 * items, and will automatically convert RaygunModules in the recipes to their
 * associated item, throwing an IllegalArgumentException if that item is null.
 */
object ScalaShapedRecipeFactory {

  def apply( result : ItemStack, recipe : Any* ) : ShapedRecipes = {
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
    case ( c : Char ) :: ( i : RaygunModule ) :: tail => {
      require( i.item != null )
      parseMappings( tail ) + ( c -> new ItemStack( i.item ) )
    }
    case ( c : Char, i : Item ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char, i : Block ) :: tail => parseMappings( tail ) + ( c -> new ItemStack( i ) )
    case ( c : Char, i : ItemStack ) :: tail => parseMappings( tail ) + ( c -> i )
    case ( c : Char, i : RaygunModule ) :: tail => {
      require( i.item != null )
      parseMappings( tail ) + ( c -> new ItemStack( i.item ) )
    }
    case _ => Map()
  }
}