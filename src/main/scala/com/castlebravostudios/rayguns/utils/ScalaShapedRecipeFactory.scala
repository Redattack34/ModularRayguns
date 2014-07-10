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

package com.castlebravostudios.rayguns.utils

import scala.collection.JavaConverters.seqAsJavaListConverter

import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraftforge.oredict.ShapedOreRecipe

/**
 * A factory for ShapedRecipes which handles Scala varargs better than the default.
 * Also can understand recipes where tuples are used to map between characters and
 * items, and will automatically convert RaygunModules in the recipes to their
 * associated item, throwing an IllegalArgumentException if that item is None.
 */
object ScalaShapedRecipeFactory {

  def shaped( result : ItemStack, recipe : Any* ) : ShapedRecipes = {
    val (strings, mappings) = separateRecipe( List( recipe:_* ) )
    val (width : Int, height : Int) = dimensions( strings )
    val map : Map[Char, ItemStack] = parseMappings( mappings )

    val items = Array( strings.mkString.toList.map( c => map.get(c).orNull ):_* )
    new ShapedRecipes( width, height, items, result )
  }

  def shapedOre( result : ItemStack, recipe : Any* ) : ShapedOreRecipe = {
    val flattened = recipe.flatMap{
      case ( c : Any, i : Any ) => Seq( c, i )
      case ( x : Any ) => Seq( x )
    }
    val mapped = flattened.map{
      case i : Item => i.asStack
      case b : Block => b.asStack
      case m : RaygunModule => m.item.get.asStack
      case x : Any => x
    }

    ScalaRecipeVarargWorkaround.createShaped( result, mapped.asJava )
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
    case ( c : Char ) :: ( i : Item ) :: tail => parseMappings( tail ) + ( c -> i.asStack )
    case ( c : Char ) :: ( i : Block ) :: tail => parseMappings( tail ) + ( c -> i.asStack )
    case ( c : Char ) :: ( i : ItemStack ) :: tail => parseMappings( tail ) + ( c -> i )
    case ( c : Char ) :: ( i : RaygunModule ) :: tail => {
      require( i.item.isDefined )
      parseMappings( tail ) + ( c -> i.item.get.asStack )
    }
    case ( c : Char, i : Item ) :: tail => parseMappings( tail ) + ( c -> i.asStack )
    case ( c : Char, i : Block ) :: tail => parseMappings( tail ) + ( c -> i.asStack )
    case ( c : Char, i : ItemStack ) :: tail => parseMappings( tail ) + ( c -> i )
    case ( c : Char, i : RaygunModule ) :: tail => {
      require( i.item.isDefined )
      parseMappings( tail ) + ( c -> i.item.get.asStack )
    }
    case _ => Map()
  }
}