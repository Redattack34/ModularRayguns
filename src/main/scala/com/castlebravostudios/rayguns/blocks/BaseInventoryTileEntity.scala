package com.castlebravostudios.rayguns.blocks

import net.minecraft.tileentity.TileEntity
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagList
import scala.collection._

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
    val isThis = worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) == this
    val isCloseEnough =  player.getDistanceSq( xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64
    isThis && isCloseEnough
  }

  override def openChest : Unit = Unit
  override def closeChest : Unit = Unit

  override def readFromNBT( tagCompound : NBTTagCompound ) : Unit = {
    super.readFromNBT(tagCompound)

    val tagList = tagCompound.getTagList("Inventory")
    for ( x <- 0 until tagList.tagCount();
          tag = tagList.tagAt(x).asInstanceOf[NBTTagCompound] ) {
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