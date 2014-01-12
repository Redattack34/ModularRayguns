package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.ExplosiveEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ExplosiveChamber extends BaseChamber {
  val moduleKey = "ExplosiveChamber"
  val powerModifier = 10.0
  val shotEffect = ExplosiveEffect
  val nameSegmentKey = "rayguns.ExplosiveChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ExplosiveChamber")
    .setTextureName("rayguns:chamber_explosive")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = registerSingleShotHandlers()
}