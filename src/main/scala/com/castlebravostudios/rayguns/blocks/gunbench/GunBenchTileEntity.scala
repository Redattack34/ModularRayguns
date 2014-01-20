package com.castlebravostudios.rayguns.blocks.gunbench

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.misc.BrokenGun
import scala.Array.canBuildFrom
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

class GunBenchTileEntity extends BaseInventoryTileEntity {
  private[this] val inv = Array.fill[ItemStack](6)(null)

  import RaygunNbtUtils._
  import GunBenchTileEntity._

  override def getSizeInventory : Int = inv.length
  override def getStackInSlot( slot : Int ) : ItemStack = inv(slot)
  override def setInventorySlotContents( slot : Int, stack : ItemStack ) = {
    inv(slot) = stack
    if ( stack != null && stack.stackSize > getInventoryStackLimit() ) {
      stack.stackSize = getInventoryStackLimit()
    }
  }

  def onSlotChanged( slot : Int ) : Unit = {
      def toStack( module : RaygunModule ) = new ItemStack( module.item, 1 )
      def setSlot( slot : Int ) ( item : ItemStack ) = setInventorySlotContents( slot, item )
      if ( slot == OUTPUT_SLOT && inv(OUTPUT_SLOT) != null ) {
        val components = getAllValidComponents( inv(OUTPUT_SLOT) )
        components.body.map(toStack).foreach(setSlot(BODY_SLOT))
        components.chamber.map(toStack).foreach(setSlot(CHAMBER_SLOT))
        components.battery.map(toStack).foreach(setSlot(BATTERY_SLOT))
        copyCharge( inv(OUTPUT_SLOT), inv(BATTERY_SLOT) )
        components.lens.map(toStack).foreach(setSlot(LENS_SLOT))
        components.acc.map(toStack).foreach(setSlot(ACC_SLOT))
      }

      val components = GunComponents( body, chamber, battery, lens, accessory )

      val gunStack = buildGun( components ).orNull
      copyCharge( inv(BATTERY_SLOT), gunStack )
      setInventorySlotContents( OUTPUT_SLOT, gunStack )
  }

  private def copyCharge( from : ItemStack, to : ItemStack ) : Unit = {
    if ( from != null && to != null ) {
      val battery = getBattery( from ).orElse( getBattery( to ) )
      battery.foreach { batt =>
        batt.setChargeDepleted( to, batt.getChargeDepleted( from ) )
      }
    }
  }

  private def getBattery( item : ItemStack ) : Option[RaygunBattery] = {
    item.getItem() match {
      case RayGun => RaygunNbtUtils.getBattery( item )
      case itemModule : ItemModule if itemModule.module.isInstanceOf[RaygunBattery] =>
        Some( itemModule.module.asInstanceOf[RaygunBattery])
      case _ => None
    }
  }

  def onPickedUpFrom( slot : Int ) : Unit = {
    if ( slot == OUTPUT_SLOT ) {
      for ( slot <- 0 until getSizeInventory ) setInventorySlotContents(slot, null)
    }
  }

  private def body = getModule(BODY_SLOT).asInstanceOf[RaygunBody]
  private def chamber = getModule(CHAMBER_SLOT).asInstanceOf[RaygunChamber]
  private def battery = getModule(BATTERY_SLOT).asInstanceOf[RaygunBattery]
  private def lens = Option( getModule(LENS_SLOT).asInstanceOf[RaygunLens] )
  private def accessory = Option( getModule(ACC_SLOT).asInstanceOf[RaygunAccessory] )

  private def getModule( slot : Int ) : RaygunModule = {
    val stack = inv(slot)
    if ( stack == null ) null else {
      val item = Item.itemsList(stack.itemID)
      item match {
        case i : ItemModule => i.module
        case _ => null
      }
    }
  }

  override def getInventoryStackLimit() : Int = 1

  override def getInvName : String = "rayguns.gunBenchEntity"
  override def isInvNameLocalized : Boolean = false
  override def isItemValidForSlot(slot : Int, stack : ItemStack) : Boolean ={
    val item = stack.getItem
    val module = item match {
      case i : ItemModule => i.module
      case _ => null
    }
    slot match {
      case BODY_SLOT => module.isInstanceOf[RaygunBody]
      case LENS_SLOT => module.isInstanceOf[RaygunLens]
      case CHAMBER_SLOT => module.isInstanceOf[RaygunChamber]
      case BATTERY_SLOT => module.isInstanceOf[RaygunBattery]
      case ACC_SLOT => module.isInstanceOf[RaygunAccessory]
      case OUTPUT_SLOT => ( item == RayGun || item == BrokenGun ) &&
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
