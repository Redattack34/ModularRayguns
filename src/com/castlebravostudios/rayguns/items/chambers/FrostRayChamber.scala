package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.FrostRayEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item


object FrostRayChamber extends BaseChamber( Config.chamberFrostRay ) {
  val moduleKey = "FrostRayChamber"
  val powerModifier = 2.0
  val shotEffect = FrostRayEffect

  setUnlocalizedName("rayguns.FrostRayChamber")
  setTextureName("rayguns:chamber_frost_ray")

  register
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.frostRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
