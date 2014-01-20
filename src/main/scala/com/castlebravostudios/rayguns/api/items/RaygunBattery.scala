package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.utils.Extensions.ItemStackExtension

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

trait RaygunBattery extends RaygunModule {

  import RaygunBattery._

  val chargeDepleted = "ChargeDepleted"

  def maxCapacity : Int
  def maxChargePerTick : Int

  def drainPower( player : EntityPlayer, item : ItemStack, event : FireEvent ) : Boolean = {
    val powerMult = event.powerMultiplier
    val powerDrain = powerMult * powerBase
    if ( getChargeDepleted( item ) + powerDrain <= maxCapacity ) {
      addCharge( item, -powerDrain.intValue() )
      true
    }
    else false
  }


  def getChargeDepleted( item : ItemStack ) : Int = {
    val tag = item.getTagCompoundSafe
    if ( !tag.hasKey( chargeDepleted ) ) {
      setChargeDepleted( item, 0 )
    }
    tag.getInteger( chargeDepleted )
  }

  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = {
    def clamp( min : Int, cur : Int, max : Int ) : Int =
      if ( cur < min ) min
      else if ( cur > max ) max
      else cur

    val actualCharge = clamp( 0, depleted, maxCapacity )

    item.getTagCompoundSafe.setInteger( chargeDepleted, actualCharge )
  }

  def addCharge( item : ItemStack, delta : Int ) : Unit =
    setChargeDepleted( item, getChargeDepleted( item ) - delta )

  def getChargeString( item : ItemStack ) : String = {
    ( (maxCapacity - getChargeDepleted( item ) ) + "/" + maxCapacity )
  }
}
object RaygunBattery {
  val powerBase = 10
}