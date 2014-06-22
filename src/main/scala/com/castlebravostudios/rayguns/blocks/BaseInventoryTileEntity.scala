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

package com.castlebravostudios.rayguns.blocks

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList

import net.minecraft.tileentity.TileEntity

abstract class BaseInventoryTileEntity extends TileEntity with IInventory {

  override def decrStackSize( slot : Int, amt : Int ) : ItemStack = {
    var stack = getStackInSlot(slot)
    if ( stack != null ) {
      if ( stack.stackSize <= amt ) {
        setInventorySlotContents(slot, null)
      }
      else {
        stack = stack.splitStack(amt)
        if ( stack.stackSize == 0 ) {
          setInventorySlotContents(slot, null)
        }
      }
    }
    stack
  }

  override def getStackInSlotOnClosing( slot : Int ) : ItemStack = {
    val stack = getStackInSlot(slot)
    if ( stack != null ) {
      setInventorySlotContents(slot, null)
    }
    stack
  }

  override def onInventoryChanged : Unit = Unit

  override def isUseableByPlayer( player : EntityPlayer ) : Boolean = {
    val isThis = worldObj.getTileEntity( xCoord, yCoord, zCoord ) == this
    val isCloseEnough =  player.getDistanceSq( xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64
    isThis && isCloseEnough
  }

  override def openChest : Unit = Unit
  override def closeChest : Unit = Unit

  override def readFromNBT( tagCompound : NBTTagCompound ) : Unit = {
    super.readFromNBT(tagCompound)

    val tagList = tagCompound.getTagList("Inventory", 10)
    for ( x <- 0 until tagList.tagCount();
          tag = tagList.getCompoundTagAt( x ) ) {
      val slot = tag.getByte("Slot")
      if ( slot >= 0 && slot < getSizeInventory ) {
        setInventorySlotContents( slot, ItemStack.loadItemStackFromNBT(tag) )
      }
    }
  }

  override def writeToNBT( tagCompound : NBTTagCompound ) {
    super.writeToNBT(tagCompound)

    val itemList = new NBTTagList
    for { index <- 0 until getSizeInventory
          item <- Option(getStackInSlot(index) ) } {
      val tag = new NBTTagCompound
      tag.setByte( "Slot", index.toByte )
      item.writeToNBT(tag)
      itemList.appendTag( tag )
    }
    tagCompound.setTag("Inventory", itemList)
  }

  def onSlotChanged( slot : Int ) : Unit
  def onPickedUpFrom( slot : Int ) : Unit
}