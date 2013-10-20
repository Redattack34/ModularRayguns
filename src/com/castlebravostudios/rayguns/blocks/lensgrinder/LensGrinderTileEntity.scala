package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity

import net.minecraft.item.ItemStack

class LensGrinderTileEntity extends BaseInventoryTileEntity {

  import LensGrinderTileEntity._

  protected[this] val inv : Array[ItemStack] = Array.fill(10)(null)

  def onPickedUpFrom( slot : Int ) : Unit = Unit
  def onSlotChanged( slot : Int ) : Unit = Unit

  override def updateEntity() : Unit = Unit

  val getInventoryStackLimit = 64

  override def getInvName : String = "rayguns.lensGrinderEntity"
  override def isInvNameLocalized : Boolean = false

  override def isItemValidForSlot( slot : Int, item : ItemStack ) : Boolean =
    slot != OUTPUT_SLOT
}
object LensGrinderTileEntity {

  val OUTPUT_SLOT = 9
}