package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object DeathRayChamber extends BaseChamber {
  val moduleKey = "DeathRayChamber"
  val powerModifier = 5.0
  val shotEffect = DeathRayEffect
  val nameSegmentKey = "rayguns.DeathRayChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.DeathRayChamber")
    .setTextureName("rayguns:chamber_death_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
  }
}