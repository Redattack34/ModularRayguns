package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.mod.ModularRayguns

object FireflyBody extends BaseRaygunModule with RaygunBody {
  val moduleKey = "FireflyBody"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.FireflyBody.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.FireflyBody")
    .setTextureName("rayguns:body_firefly")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}