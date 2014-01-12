package com.castlebravostudios.rayguns.blocks

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class GuiBlockSlot( inv: BaseInventoryTileEntity, slot : Int, x : Int, y : Int ) extends Slot( inv, slot, x, y ) {
  override def isItemValid( stack : ItemStack ) : Boolean = inv.isItemValidForSlot( slot, stack )
  override def onSlotChanged() = {
    super.onSlotChanged
    inv.onSlotChanged( slot )
  }
  override def onPickupFromSlot(player : EntityPlayer, item : ItemStack ) : Unit = {
    super.onPickupFromSlot(player, item)
    inv.onPickedUpFrom(slot)
  }
}
abstract class BaseContainer( inventoryPlayer : InventoryPlayer, entity : BaseInventoryTileEntity ) extends Container {

  def lastCustomIndex : Int

  protected def addPlayerInventory( offsetX: Int = 0, offsetY: Int = 0 ) = {
    for { i <- 0 until 3
          j <- 0 until 9 } {
      addSlotToContainer(new Slot( inventoryPlayer, j + i * 9 + 9,
          (8 + j * 18) + offsetX,
          (84 + i * 18) + offsetY ) )
    }

    for ( i <- 0 until 9 ) {
      addSlotToContainer( new Slot( inventoryPlayer, i,
          (8 + i * 18) + offsetX, 142 + offsetY ) )
    }
  }

  override def canInteractWith( player : EntityPlayer ) = entity.isUseableByPlayer( player )

  override def transferStackInSlot( player : EntityPlayer, slot : Int ) : ItemStack = {
    val slotObject : Slot = inventorySlots.get( slot ).asInstanceOf[Slot]

    if ( slotObject == null || !slotObject.getHasStack() ) {
      return null
    }

    val stackInSlot = slotObject.getStack
    val copyStack = stackInSlot.copy

    def shouldMergeToMainInventory = slot <= lastCustomIndex || slot > lastCustomIndex + 27
    def doMergeToMainInventory( stack : ItemStack ) =
      mergeItemStack( stack, lastCustomIndex + 1, lastCustomIndex + 27 + 1, true )

    val changed =
      if ( shouldMergeToMainInventory ) doMergeToMainInventory( stackInSlot )
      else transferStackToCustomSlots( player, slot, stackInSlot )

    if ( changed ) {
      updateSlot( player, slotObject, stackInSlot, copyStack )
    }
    null
  }

  private def updateSlot( player: EntityPlayer, slotObject: Slot, changedStack : ItemStack, copyStack : ItemStack ) : Unit = {
    if ( changedStack.stackSize == 0 ) {
      slotObject.putStack( null )
    }
    else {
      slotObject.onSlotChanged()
    }

    if ( changedStack.stackSize != copyStack.stackSize ) {
      slotObject.onPickupFromSlot(player, changedStack)
    }
  }

  /**
   * Merge the given stack to one of the custom slots. Return true if the slot was modified.
   */
  protected def transferStackToCustomSlots( player : EntityPlayer, slot : Int, stackInSlot: ItemStack ) : Boolean = {
    mergeItemStack( stackInSlot, 0, lastCustomIndex, false );
  }


  /**
   * Steps through this inventory, walking slots from start to end (unless
   * reverse is true, in which case it's the other way round) and attempts to
   * merge the given stack into any inventory slot it can. Does not respect
   * entity.isItemValidForSlot. Will never touch the end index, so you may need
   * to add one. In Scala range terms, it goes from 'start until end' or
   * '(end - 1) to start). This does modify the given stack if it can. This will
   * return true if the stack was modified, false if it wasn't.
   */
  //Overridden to provide extra documentation.
  protected override def mergeItemStack( stack : ItemStack, start : Int, end : Int, reverse : Boolean ) : Boolean =
    super.mergeItemStack(stack, start, end, reverse);
}