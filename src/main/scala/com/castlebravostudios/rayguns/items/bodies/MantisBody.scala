package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.mod.ModularRayguns

object MantisBody extends BaseRaygunModule with RaygunBody {
  val moduleKey = "MantisBody"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.MantisBody.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.MantisBody")
    .setTextureName("rayguns:body_mantis")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}