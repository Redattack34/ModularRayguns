package com.castlebravostudios.rayguns.blocks

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemFocus
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.items.RayGun
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.items.Items
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.items.BrokenGun

class GunBenchTileEntity extends TileEntity with IInventory {
  private val inv : Array[ItemStack] = Array.fill(6)(null)

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

  override def onInventoryChanged : Unit = Unit

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

  def copyDamage( from : ItemStack, to : ItemStack ) : Unit = {
    if ( from != null && to != null ) {
      to.setItemDamage( from.getItemDamage )
    }
  }

  def onPickedUpfrom( slot : Int ) : Unit = {
    if ( slot == OUTPUT_SLOT ) {
      for ( slot <- 0 until getSizeInventory ) setInventorySlotContents(slot, null)
    }
  }

  def body = getItem(BODY_SLOT).asInstanceOf[ItemBody]
  def chamber = getItem(CHAMBER_SLOT).asInstanceOf[ItemChamber]
  def battery = getItem(BATTERY_SLOT).asInstanceOf[ItemBattery]
  def lens = Option( getItem(LENS_SLOT).asInstanceOf[ItemFocus] )
  def accessory = Option( getItem(ACC_SLOT).asInstanceOf[ItemAccessory] )

  private def getItem( slot : Int ) : Item = {
    val stack = inv(slot)
    if ( stack == null ) null else Item.itemsList(stack.itemID)
  }

  override def getStackInSlotOnClosing( slot : Int ) : ItemStack = {
    val stack = getStackInSlot(slot)
    if ( stack != null ) {
      setInventorySlotContents(slot, null)
    }
    stack
  }

  override def getInventoryStackLimit() : Int = 1

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
      if ( slot >= 0 && slot < inv.length ) {
        inv(slot) = ItemStack.loadItemStackFromNBT(tag)
      }
    }
  }

  override def writeToNBT( tagCompound : NBTTagCompound ) {
    super.writeToNBT(tagCompound)

    val itemList = new NBTTagList
    for { (item, index) <- inv.zipWithIndex
          if item != null } {
      val tag = new NBTTagCompound
      tag.setByte( "Slot", index.toByte )
      item.writeToNBT(tag)
      itemList.appendTag( tag )
    }
  }

  override def getInvName : String = "rayguns.gunBenchEntity"
  override def isInvNameLocalized : Boolean = false
  override def isItemValidForSlot(slot : Int, stack : ItemStack) : Boolean ={
    val item = stack.getItem
    slot match {
      case BODY_SLOT => item.isInstanceOf[ItemBody]
      case LENS_SLOT => item.isInstanceOf[ItemFocus]
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
