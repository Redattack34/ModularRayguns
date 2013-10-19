package com.castlebravostudios.rayguns.blocks.gunbench

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.BrokenGun
import scala.Array.canBuildFrom
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.items.RayGun
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity

class GunBenchTileEntity extends BaseInventoryTileEntity {
  protected[this] val inv : Array[ItemStack] = Array.fill(6)(null)

  import RaygunNbtUtils._
  import GunBenchTileEntity._

  def onSlotChanged( slot : Int ) : Unit = {
      def toStack( item : Item ) = new ItemStack( item, 1 )
      def setSlot( slot : Int ) ( item : ItemStack ) = setInventorySlotContents( slot, item )
      if ( slot == OUTPUT_SLOT && inv(OUTPUT_SLOT) != null ) {
        val components = getAllValidComponents( inv(OUTPUT_SLOT) )
        components.body.map(toStack).foreach(setSlot(BODY_SLOT))
        components.chamber.map(toStack).foreach(setSlot(CHAMBER_SLOT))
        components.battery.map(toStack).foreach(setSlot(BATTERY_SLOT))
        copyDamage( inv(OUTPUT_SLOT), inv(BATTERY_SLOT) )
        components.lens.map(toStack).foreach(setSlot(LENS_SLOT))
        components.acc.map(toStack).foreach(setSlot(ACC_SLOT))
      }

      val components = GunComponents( body, chamber, battery, lens, accessory )

      val gunStack = buildGun( components ).orNull
      copyDamage( inv(BATTERY_SLOT), gunStack )
      setInventorySlotContents( OUTPUT_SLOT, gunStack )
  }

  private def copyDamage( from : ItemStack, to : ItemStack ) : Unit = {
    if ( from != null && to != null ) {
      to.setItemDamage( from.getItemDamage )
    }
  }

  def onPickedUpFrom( slot : Int ) : Unit = {
    if ( slot == OUTPUT_SLOT ) {
      for ( slot <- 0 until getSizeInventory ) setInventorySlotContents(slot, null)
    }
  }

  private def body = getItem(BODY_SLOT).asInstanceOf[ItemBody]
  private def chamber = getItem(CHAMBER_SLOT).asInstanceOf[ItemChamber]
  private def battery = getItem(BATTERY_SLOT).asInstanceOf[ItemBattery]
  private def lens = Option( getItem(LENS_SLOT).asInstanceOf[ItemLens] )
  private def accessory = Option( getItem(ACC_SLOT).asInstanceOf[ItemAccessory] )

  private def getItem( slot : Int ) : Item = {
    val stack = inv(slot)
    if ( stack == null ) null else Item.itemsList(stack.itemID)
  }

  override def getInventoryStackLimit() : Int = 1

  override def getInvName : String = "rayguns.gunBenchEntity"
  override def isInvNameLocalized : Boolean = false
  override def isItemValidForSlot(slot : Int, stack : ItemStack) : Boolean ={
    val item = stack.getItem
    slot match {
      case BODY_SLOT => item.isInstanceOf[ItemBody]
      case LENS_SLOT => item.isInstanceOf[ItemLens]
      case CHAMBER_SLOT => item.isInstanceOf[ItemChamber]
      case BATTERY_SLOT => item.isInstanceOf[ItemBattery]
      case ACC_SLOT => item.isInstanceOf[ItemAccessory]
      case OUTPUT_SLOT => ( item.isInstanceOf[RayGun] || item.isInstanceOf[BrokenGun] ) &&
                          inv.forall( _ == null )
    }
  }
}
object GunBenchTileEntity {

  val BODY_SLOT = 0
  val LENS_SLOT = 1
  val CHAMBER_SLOT = 2
  val BATTERY_SLOT = 3
  val ACC_SLOT = 4
  val OUTPUT_SLOT = 5
}
