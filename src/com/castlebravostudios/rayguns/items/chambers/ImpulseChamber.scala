package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.effects.ImpulseEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._

object ImpulseChamber extends BaseChamber( Config.chamberImpulse ) {
  val moduleKey = "ImpulseChamber"
  val powerModifier = 1.5
  val shotEffect = ImpulseEffect

  setUnlocalizedName("rayguns.ImpulseChamber")
  setTextureName("rayguns:chamber_impulse")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.impulseEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}