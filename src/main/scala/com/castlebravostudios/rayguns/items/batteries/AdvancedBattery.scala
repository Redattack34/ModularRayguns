package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector

object AdvancedBattery extends BaseRaygunModule with RaygunBattery {

  val moduleKey = "AdvancedBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.AdvancedBattery.segment"
  val maxCapacity = 3000

  def createItem( id : Int ) = ( new ItemModule( id, this ) with RFItemPowerConnector with IC2ItemPowerConnector )
    .setMaxDamage( maxCapacity )
    .setUnlocalizedName("rayguns.AdvancedBattery")
    .setTextureName("rayguns:battery_advanced")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}