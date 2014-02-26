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

package com.castlebravostudios.rayguns.blocks.gunbench

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity
import com.castlebravostudios.rayguns.items.misc.BrokenGun
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunBarrel

class GunBenchTileEntity extends BaseInventoryTileEntity {
  private[this] val inv = Array.fill[ItemStack](7)(null)

  //scalastyle:off import.grouping
  import GunBenchTileEntity.{BODY_SLOT, LENS_SLOT, CHAMBER_SLOT,
    BATTERY_SLOT, ACC_SLOT, BARREL_SLOT, OUTPUT_SLOT}
  //scalastyle:on import.grouping

  override def getSizeInventory : Int = inv.length
  override def getStackInSlot( slot : Int ) : ItemStack = inv(slot)
  override def setInventorySlotContents( slot : Int, stack : ItemStack ) : Unit = {
    inv(slot) = stack
    if ( stack != null && stack.stackSize > getInventoryStackLimit() ) {
      stack.stackSize = getInventoryStackLimit()
    }
  }

  def onSlotChanged( slot : Int ) : Unit = {
      def toStack( module : RaygunModule ) : ItemStack = module.item match {
        case Some( item ) => new ItemStack( item, 1 )
        case None => null
      }
      def setSlot( slot : Int ) ( item : ItemStack ) : Unit = setInventorySlotContents( slot, item )

      if ( slot == OUTPUT_SLOT && inv(OUTPUT_SLOT) != null ) {
        val components = RaygunNbtUtils.getAllValidComponents( inv(OUTPUT_SLOT) )
        components.body.map(toStack).foreach(setSlot(BODY_SLOT))
        components.chamber.map(toStack).foreach(setSlot(CHAMBER_SLOT))
        components.battery.map(toStack).foreach(setSlot(BATTERY_SLOT))
        copyCharge( inv(OUTPUT_SLOT), inv(BATTERY_SLOT) )
        components.lens.map(toStack).foreach(setSlot(LENS_SLOT))
        components.acc.map(toStack).foreach(setSlot(ACC_SLOT))
        components.barrel.map(toStack).foreach(setSlot(BARREL_SLOT))
      }

      val components = GunComponents( body, chamber, battery, barrel, lens, accessory )

      val gunStack = RaygunNbtUtils.buildGun( components ).orNull
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
  private def barrel = getModule(BARREL_SLOT).asInstanceOf[RaygunBarrel]

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

    if ( slot == OUTPUT_SLOT ) ( item == RayGun || item == BrokenGun ) && inv.forall( _ == null )
    else slotMatchesModule( slot, module )
  }

  private def slotMatchesModule( slot : Int, module : RaygunModule ) : Boolean = slot match {
    case BODY_SLOT => module.isInstanceOf[RaygunBody]
    case LENS_SLOT => module.isInstanceOf[RaygunLens]
    case CHAMBER_SLOT => module.isInstanceOf[RaygunChamber]
    case BATTERY_SLOT => module.isInstanceOf[RaygunBattery]
    case ACC_SLOT => module.isInstanceOf[RaygunAccessory]
    case BARREL_SLOT => module.isInstanceOf[RaygunBarrel]
  }

}
object GunBenchTileEntity {

  val BODY_SLOT = 0
  val LENS_SLOT = 1
  val CHAMBER_SLOT = 2
  val BATTERY_SLOT = 3
  val ACC_SLOT = 4
  val BARREL_SLOT = 5
  val OUTPUT_SLOT = 6
}
