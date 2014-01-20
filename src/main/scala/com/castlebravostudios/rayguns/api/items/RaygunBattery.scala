package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

trait RaygunBattery extends RaygunModule {

  import RaygunBattery._

  def maxCapacity : Int

  def drainPower( player : EntityPlayer, item : ItemStack, event : FireEvent ) : Boolean = {
    val powerMult = event.powerMultiplier
    val powerDrain = powerMult * powerBase
    if ( getChargeDepleted( item ) + powerDrain <= maxCapacity ) {
      addCharge( item, -powerDrain.intValue() )
      true
    }
    else false
  }

  def getChargeDepleted( item : ItemStack ) : Int = RaygunNbtUtils.getChargeDepleted( item )
  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = RaygunNbtUtils.setChargeDepleted(depleted, item)
  def addCharge( item : ItemStack, delta : Int ) : Unit = RaygunNbtUtils.addCharge(delta, item)
}
object RaygunBattery {
  val powerBase = 10
}