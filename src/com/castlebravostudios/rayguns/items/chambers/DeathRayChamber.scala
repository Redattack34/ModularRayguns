package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

object DeathRayChamber extends BaseChamber( Config.chamberDeathRay ) {
  val moduleKey = "DeathRayChamber"
  val powerModifier = 5.0
  val shotEffect = DeathRayEffect
  setUnlocalizedName("rayguns.DeathRayChamber")
  setTextureName("rayguns:chamber_death_ray")

  register
  RecipeRegisterer.registerChamber(Tier3, this, Emitters.deathRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
}