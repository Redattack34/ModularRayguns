package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.mod.ModularRayguns

object PreciseLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "PreciseLens"
  val powerModifier = 1.5
  val nameSegmentKey = "rayguns.PreciseLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.PreciseLens")
    .setTextureName("rayguns:lens_precise")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}