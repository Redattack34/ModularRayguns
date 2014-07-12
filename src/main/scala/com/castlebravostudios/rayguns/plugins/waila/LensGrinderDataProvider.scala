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

package com.castlebravostudios.rayguns.plugins.waila

import mcp.mobius.waila.api.IWailaDataProvider
import net.minecraft.item.ItemStack
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinder
import com.castlebravostudios.rayguns.items.RaygunsBlocks
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity

object LensGrinderDataProvider extends IWailaDataProvider {

  def getWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack =
    RaygunsBlocks.lensGrinder.asStack

  def getWailaHead(stack: ItemStack, currentTip: java.util.List[String],
      accessor: IWailaDataAccessor, config: IWailaConfigHandler): java.util.List[String] = currentTip

  def getWailaBody(stack: ItemStack, currentTip: java.util.List[String],
      accessor: IWailaDataAccessor, config: IWailaConfigHandler): java.util.List[String] = {
      val te = accessor.getTileEntity()
      val lg = te.asInstanceOf[LensGrinderTileEntity]

      val hasPower = lg.chargeStored > 0
      val recipe = lg.recipe
      val progress = recipe.map( _ -> lg.getTimeRemainingScaled(100) ).getOrElse( 0 )
      val crafting = recipe.map( _.recipe.getRecipeOutput().getDisplayName() ).getOrElse( "None" )

      currentTip.add( s"Has Power:  $hasPower" )
      currentTip.add( s"Progress:   $progress%" )
      currentTip.add( s"Crafting:   $crafting" )

      currentTip
  }

  def getWailaTail(stack: ItemStack, currentTip: java.util.List[String],
      accessor: IWailaDataAccessor, config: IWailaConfigHandler): java.util.List[String] = currentTip
}