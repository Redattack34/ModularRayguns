package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

trait ItemBattery extends ItemModule {

  import ItemBattery._
  import RaygunNbtUtils._

  def maxCapacity = getMaxDamage

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0

  def drainPower( player : EntityPlayer, item : ItemStack, comp : GunComponents ) : Boolean = {
    val powerMult = comp.powerMultiplier
    val powerDrain = powerMult * powerBase
    if ( getChargeDepleted( item ) + powerDrain <= maxCapacity ) {
      addCharge( -powerDrain.intValue(), item )
      true
    }
    else false
  }
}
object ItemBattery {
  val powerBase = 10
}