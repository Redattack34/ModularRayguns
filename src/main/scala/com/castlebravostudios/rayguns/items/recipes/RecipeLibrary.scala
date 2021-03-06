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

package com.castlebravostudios.rayguns.items.recipes

import scala.collection.JavaConverters.seqAsJavaListConverter

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions
import com.castlebravostudios.rayguns.utils.ScalaShapedRecipeFactory
import com.castlebravostudios.rayguns.utils.ScalaRecipeVarargWorkaround

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapelessRecipes

import cpw.mods.fml.common.registry.GameRegistry

trait RecipeLibrary {

  def registerRecipes() : Unit

  def getIngredientItems() : Seq[(Item, String)]

  protected def addModuleShaped( module : RaygunModule, params : Any* ) : Unit = {
    val modules = findModules( module, params :_* )

    //Skip modules where the module or a recipe ingredient has been disabled.
    if ( modules.exists( _.item.isEmpty ) ) {
      return
    }

    GameRegistry.addRecipe( ScalaShapedRecipeFactory.shaped(
        module.item.get.asStack, params:_* ) );
  }

  protected def addModuleShapedOre( module : RaygunModule, params : Any* ) : Unit = {
    val modules = findModules( module, params :_* )

    //Skip modules where the module or a recipe ingredient has been disabled.
    if ( modules.exists( _.item.isEmpty ) ) {
      return
    }

    GameRegistry.addRecipe( ScalaShapedRecipeFactory.shapedOre(
        module.item.get.asStack, params:_* ) );
  }

  protected def addModuleShapelessOre( module : RaygunModule, params : Any* ) : Unit = {
    val modules = findModules( module, params :_* )

    //Skip modules where the module or a recipe ingredient has been disabled.
    if ( modules.exists( _.item.isEmpty ) ) {
      return
    }

    addShapelessOre(module.item.get.asStack, params :_* )
  }

  protected def addModuleLensGrinder( time : Short, module : RaygunModule, params : Any* ): Unit = {
    val modules = findModules( module, params :_* )

    //Skip modules where the module or a recipe ingredient has been disabled.
    if ( modules.exists( _.item.isEmpty ) ) {
      return
    }

    LensGrinderRecipeRegistry.register( time, module.item.get.asStack, params :_* );
  }

  private def findModules( module : RaygunModule, params : Any* ) : Seq[RaygunModule] = {
    module +: params.flatMap{
      case mod : RaygunModule => Some( mod )
      case (c, mod : RaygunModule) => Some( mod )
      case _ => None
    }
  }

  protected def addShaped( output : ItemStack, recipe : Any* ) : Unit =
    GameRegistry.addRecipe( ScalaShapedRecipeFactory.shaped( output, recipe :_* ) )

  protected def addShapedOre( output : ItemStack, recipe : Any* ) : Unit =
    GameRegistry.addRecipe( ScalaShapedRecipeFactory.shapedOre( output, recipe :_* ) )

  protected def addShapeless( output : ItemStack, recipe : Any* ) : Unit = {
    val stacks = recipe.map{
      case item : Item => item.asStack
      case block : Block => block.asStack
      case stack : ItemStack => stack.copy
      case module : RaygunModule => {
        require( module.item.isDefined )
        module.item.get.asStack
      }
      case _ => throw new IllegalArgumentException( "Invalid shapeless recipe." );
    }
    GameRegistry.addRecipe( new ShapelessRecipes( output, stacks.asJava ) )
  }

  protected def addShapelessOre( output : ItemStack, recipe : Any* ) : Unit = {
    val stacks = recipe.map{
      case item : Item => item.asStack
      case block : Block => block.asStack
      case stack : ItemStack => stack.copy
      case module : RaygunModule => {
        require( module.item.isDefined )
        module.item.get.asStack
      }
      case str : String => str
      case k : Any => throw new IllegalArgumentException( s"Invalid shapeless recipe. Unknown type: $k" );
    }
    GameRegistry.addRecipe( ScalaRecipeVarargWorkaround.createShapeless( output, stacks.asJava ) )
  }

  protected def addLensGrinder( ticks : Short, result : ItemStack, recipe : Any* ): Unit =
    LensGrinderRecipeRegistry.register( ticks, result, recipe : _ * )

  protected def addSmelting( input : Block, output : ItemStack, expMult : Float ): Unit =
    GameRegistry.addSmelting(input, output, expMult)

  protected def addSmelting( input : Item, output : ItemStack, expMult : Float ): Unit =
    GameRegistry.addSmelting(input, output, expMult)
}