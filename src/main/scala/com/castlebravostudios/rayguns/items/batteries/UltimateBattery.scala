package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector

object UltimateBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "UltimateBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.UltimateBattery.segment"
  val maxCapacity = 5000

  def createItem( id : Int ) = ( new ItemModule( id, this ) with RFItemPowerConnector with IC2ItemPowerConnector )
    .setMaxDamage( maxCapacity )
    .setUnlocalizedName("rayguns.UltimateBattery")
    .setTextureName("rayguns:battery_ultimate")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}