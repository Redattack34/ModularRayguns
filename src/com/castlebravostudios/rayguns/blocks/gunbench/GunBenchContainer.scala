package com.castlebravostudios.rayguns.blocks.gunbench

import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.items.RayGun
import GunBenchTileEntity.ACC_SLOT
import GunBenchTileEntity.BATTERY_SLOT
import GunBenchTileEntity.BODY_SLOT
import GunBenchTileEntity.CHAMBER_SLOT
import GunBenchTileEntity.OUTPUT_SLOT
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

class GunBenchContainer( inventoryPlayer : InventoryPlayer, entity : GunBenchTileEntity ) extends Container {

  import GunBenchTileEntity._
  import RaygunNbtUtils._

  class GunBenchSlot( inv: GunBenchTileEntity, slot : Int, x : Int, y : Int ) extends Slot( inv, slot, x, y ) {
    override def isItemValid( stack : ItemStack ) : Boolean = inv.isItemValidForSlot( slot, stack )
    override def onSlotChanged() = {
      super.onSlotChanged
      inv.onSlotChanged( slot )
    }
    override def onPickupFromSlot(player : EntityPlayer, item : ItemStack ) : Unit = {
      super.onPickupFromSlot(player, item)
      inv.onPickedUpfrom(slot)
    }
  }

  addSlotToContainer( new GunBenchSlot( entity, BODY_SLOT,     37, 19 ) )
  addSlotToContainer( new GunBenchSlot( entity, LENS_SLOT,     37, 37 ) )
  addSlotToContainer( new GunBenchSlot( entity, CHAMBER_SLOT, 106, 19 ) )
  addSlotToContainer( new GunBenchSlot( entity, BATTERY_SLOT, 106, 37 ) )
  addSlotToContainer( new GunBenchSlot( entity, ACC_SLOT,      71, 55 ) )
  addSlotToContainer( new GunBenchSlot( entity, OUTPUT_SLOT,  147, 57 ) )

  for { i <- 0 until 3
        j <- 0 until 9 } {
    addSlotToContainer(new Slot( inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 ) )
  }

  for ( i <- 0 until 9 ) {
    addSlotToContainer( new Slot( inventoryPlayer, i, 8 + i * 18, 142 ) )
  }

  override def canInteractWith( player : EntityPlayer ) = entity.isUseableByPlayer( player )

  override def transferStackInSlot( player : EntityPlayer, slot : Int ) : ItemStack = {
    val slotObject : Slot = inventorySlots.get( slot ).asInstanceOf[Slot]

    if ( slotObject != null && slotObject.getHasStack ) {
      val stackInSlot = slotObject.getStack
      val copyStack = stackInSlot.copy

      if ( slot <= OUTPUT_SLOT || slot > OUTPUT_SLOT + 27 ) {
        if ( !mergeItemStack( stackInSlot, OUTPUT_SLOT + 1, OUTPUT_SLOT + 27 + 1, true ) ) {
          return null
        }
      }
      else {
        val targetSlot = Item.itemsList(stackInSlot.itemID) match {
          case _: ItemBody => BODY_SLOT
          case _: ItemLens => LENS_SLOT
          case _: ItemChamber => CHAMBER_SLOT
          case _: ItemBattery => BATTERY_SLOT
          case _: ItemAccessory => ACC_SLOT
          case _: RayGun => OUTPUT_SLOT
          case _ => return null
        }

        if ( entity.isItemValidForSlot(targetSlot, stackInSlot ) ) {
          if ( !mergeItemStack( stackInSlot, targetSlot, targetSlot + 1, true ) ) {
            return null;
          }
        }
      }

      if ( stackInSlot.stackSize == 0 ) {
        slotObject.putStack( null )
      }
      else {
        slotObject.onSlotChanged()
      }

      if ( stackInSlot.stackSize == copyStack.stackSize ) {
        return null
      }
      slotObject.onPickupFromSlot(player, stackInSlot)
    }
    null
  }
}