package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.effects.LaserEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._

object LaserChamber extends BaseChamber( Config.chamberLaser ) {
  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  val shotEffect = LaserEffect

  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.laserEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}