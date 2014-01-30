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

package com.castlebravostudios.rayguns.api

import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import net.minecraft.block.Block
import net.minecraft.inventory.InventoryCrafting
import com.castlebravostudios.rayguns.utils.ScalaShapedRecipeFactory


case class LensGrinderRecipe( recipe : ShapedRecipes, ticks : Short )
object LensGrinderRecipeRegistry {

  private var _recipes = Seq[LensGrinderRecipe]()

  def recipes = _recipes
  def getRecipe( i : InventoryCrafting ) : Option[LensGrinderRecipe] =
    _recipes.find( recipe => recipe.recipe.matches( i, null ) )

  def register( ticks : Short, result : ItemStack, recipe : Any* ) : Unit =
    register( ScalaShapedRecipeFactory( result, recipe:_* ), ticks )
  def register( recipe : ShapedRecipes, ticks: Short ) : Unit =
    register( LensGrinderRecipe( recipe, ticks ) )
  def register( recipe : LensGrinderRecipe ) : Unit = _recipes = recipe +: _recipes
}