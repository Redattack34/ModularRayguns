package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.effects.HeatRayEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._


object HeatRayChamber extends BaseChamber( Config.chamberHeatRay ) {

  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  val shotEffect = HeatRayEffect

  setUnlocalizedName("rayguns.HeatRayChamber")
  setTextureName("rayguns:chamber_heat_ray")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.heatRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
