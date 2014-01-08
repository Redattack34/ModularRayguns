package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.mod.ModularRayguns

object RefireCapacitor extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "RefireCapacitor"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.RefireCapacitor.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.RefireCapacitor")
    .setTextureName("rayguns:refire_capacitor")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}