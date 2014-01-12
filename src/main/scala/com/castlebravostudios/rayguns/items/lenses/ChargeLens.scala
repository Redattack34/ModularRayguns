package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ChargeLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "ChargeLens"
  val powerModifier = 1.5
  val nameSegmentKey = "rayguns.ChargeLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ChargeLens")
    .setTextureName("rayguns:lens_charge")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}