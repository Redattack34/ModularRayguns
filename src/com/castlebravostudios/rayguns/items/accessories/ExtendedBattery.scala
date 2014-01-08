package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ExtendedBattery extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "ExtendedBattery"
  val powerModifier = 2.0 / 3.0
  val nameSegmentKey = "rayguns.ExtendedBattery.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ExtendedBattery")
    .setTextureName("rayguns:extended_battery")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}