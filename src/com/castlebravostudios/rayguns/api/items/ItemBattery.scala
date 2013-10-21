package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer

trait ItemBattery extends ItemModule {

  import ItemBattery._

  def maxCapacity = getMaxDamage

  def drainPower( player : EntityPlayer, item : ItemStack, comp : GunComponents ) : Boolean = {
    val powerMult = comp.powerMultiplier
    val powerDrain = powerMult * powerBase
    if ( item.getItemDamage() + powerDrain == maxCapacity ) {
      item.damageItem(powerDrain.intValue - 1, player)
      true
    }
    else if ( item.getItemDamage() + powerDrain < maxCapacity ) {
      item.damageItem(powerDrain.intValue, player)
      true
    }
    else false
  }
}
object ItemBattery {
  val powerBase = 10
}