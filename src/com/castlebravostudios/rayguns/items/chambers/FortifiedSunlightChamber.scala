package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._

object FortifiedSunlightChamber extends BaseChamber( Config.chamberFortifiedSunlight ) {
  val moduleKey = "FortifiedSunlightChamber"
  val powerModifier = 4.0
  val shotEffect = FortifiedSunlightEffect

  setUnlocalizedName("rayguns.FortifiedSunlightChamber")
  setTextureName("rayguns:chamber_fortified_sunlight")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.fortifiedSunlightEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}