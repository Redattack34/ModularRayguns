package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.HeatRayEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item


object HeatRayChamber extends BaseChamber( Config.chamberHeatRay ) {

  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  val shotEffect = HeatRayEffect

  setUnlocalizedName("rayguns.HeatRayChamber")
  setTextureName("rayguns:chamber_heat_ray")

  register
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.heatRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
