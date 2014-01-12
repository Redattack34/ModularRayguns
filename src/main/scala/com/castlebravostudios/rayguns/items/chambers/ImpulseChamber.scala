package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.ImpulseEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ImpulseChamber extends BaseChamber {
  val moduleKey = "ImpulseChamber"
  val powerModifier = 1.5
  val shotEffect = ImpulseEffect
  val nameSegmentKey = "rayguns.ImpulseChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ImpulseChamber")
    .setTextureName("rayguns:chamber_impulse")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}