package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.effects.EnderEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._

object EnderChamber extends BaseChamber( Config.chamberEnder ) {
  val moduleKey = "EnderChamber"
  val powerModifier = 2.0
  val shotEffect = EnderEffect
  setUnlocalizedName("rayguns.EnderChamber")
  setTextureName("rayguns:chamber_ender")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber(Tier2, this, Emitters.enderEmitter )

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}