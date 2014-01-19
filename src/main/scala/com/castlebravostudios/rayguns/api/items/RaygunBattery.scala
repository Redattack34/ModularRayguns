package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

trait RaygunBattery extends RaygunModule {

  import RaygunBattery._
  import RaygunNbtUtils._

  def maxCapacity : Int

  def drainPower( player : EntityPlayer, item : ItemStack, event : FireEvent ) : Boolean = {
    val powerMult = event.powerMultiplier
    val powerDrain = powerMult * powerBase
    if ( getChargeDepleted( item ) + powerDrain <= maxCapacity ) {
      addCharge( -powerDrain.intValue(), item )
      true
    }
    else false
  }


}
object RaygunBattery {
  val powerBase = 10
}