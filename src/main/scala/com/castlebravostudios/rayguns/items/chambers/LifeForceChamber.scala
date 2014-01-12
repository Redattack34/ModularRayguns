package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.LifeForceEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns


object LifeForceChamber extends BaseChamber {
  val moduleKey = "LifeForceChamber"
  val powerModifier = 3.0
  val shotEffect = LifeForceEffect
  val nameSegmentKey = "rayguns.LifeForceChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.LifeForceChamber")
    .setTextureName("rayguns:chamber_life_force")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}
