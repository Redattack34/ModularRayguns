package com.castlebravostudios.rayguns.blocks.gunbench

import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.blocks.BaseContainer
import com.castlebravostudios.rayguns.blocks.GuiBlockSlot
import com.castlebravostudios.rayguns.items.misc.RayGun
import GunBenchTileEntity.ACC_SLOT
import GunBenchTileEntity.BATTERY_SLOT
import GunBenchTileEntity.BODY_SLOT
import GunBenchTileEntity.CHAMBER_SLOT
import GunBenchTileEntity.LENS_SLOT
import GunBenchTileEntity.OUTPUT_SLOT
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.ItemModule

class GunBenchContainer( inventoryPlayer : InventoryPlayer, entity : GunBenchTileEntity )
  extends BaseContainer( inventoryPlayer, entity ) {

  addSlotToContainer( new GuiBlockSlot( entity, BODY_SLOT,     37, 19 ) )
  addSlotToContainer( new GuiBlockSlot( entity, LENS_SLOT,     37, 37 ) )
  addSlotToContainer( new GuiBlockSlot( entity, CHAMBER_SLOT, 106, 19 ) )
  addSlotToContainer( new GuiBlockSlot( entity, BATTERY_SLOT, 106, 37 ) )
  addSlotToContainer( new GuiBlockSlot( entity, ACC_SLOT,      71, 55 ) )
  addSlotToContainer( new GuiBlockSlot( entity, OUTPUT_SLOT,  147, 57 ) )

  addPlayerInventory()

  val lastCustomIndex = OUTPUT_SLOT

  protected override def transferStackToCustomSlots( player : EntityPlayer, slot : Int, stackInSlot: ItemStack ) : Boolean = {
    val targetSlot = Item.itemsList(stackInSlot.itemID) match {
      case RayGun => OUTPUT_SLOT
      case item : ItemModule => item.module match {
        case _: RaygunBody => BODY_SLOT
        case _: RaygunLens => LENS_SLOT
        case _: RaygunChamber => CHAMBER_SLOT
        case _: RaygunBattery => BATTERY_SLOT
        case _: RaygunAccessory => ACC_SLOT
      }
      case _ => return false
    }

    if ( entity.isItemValidForSlot(targetSlot, stackInSlot ) ) {
      mergeItemStack( stackInSlot, targetSlot, targetSlot + 1, false );
    }
    else false
  }
}