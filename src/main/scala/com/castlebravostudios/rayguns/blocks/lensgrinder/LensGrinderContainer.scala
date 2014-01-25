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

package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.blocks.BaseContainer
import com.castlebravostudios.rayguns.blocks.GuiBlockSlot

import LensGrinderTileEntity.OUTPUT_SLOT
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack

class LensGrinderContainer( inventoryPlayer : InventoryPlayer, entity : LensGrinderTileEntity )
  extends BaseContainer( inventoryPlayer, entity ) {

  import LensGrinderTileEntity._

  for { y <- ( 0 until 3 )
        x <- ( 0 until 3 )
    } {
      addSlotToContainer( new GuiBlockSlot( entity, (y * 3) + x, 30 + ( x * 18 ), 17 + ( y * 18 ) ) )
    }

  addSlotToContainer( new GuiBlockSlot( entity, OUTPUT_SLOT, 124, 35 ) )
  val lastCustomIndex = OUTPUT_SLOT

  addPlayerInventory()


  protected override def transferStackToCustomSlots( player : EntityPlayer, slot : Int, stackInSlot: ItemStack ) : Boolean = {
    mergeItemStack( stackInSlot, 0, lastCustomIndex - 1, false );
  }
}