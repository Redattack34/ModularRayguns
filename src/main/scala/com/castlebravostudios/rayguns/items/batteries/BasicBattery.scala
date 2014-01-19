package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector

object BasicBattery extends BaseRaygunModule with RaygunBattery {

  val moduleKey = "BasicBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.BasicBattery.segment"
  val maxCapacity = 1000

  def createItem( id : Int ) = new ItemBattery( id, this )
    .setMaxDamage( maxCapacity )
    .setUnlocalizedName("rayguns.BasicBattery")
    .setTextureName("rayguns:battery_basic")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

}