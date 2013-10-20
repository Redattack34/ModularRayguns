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