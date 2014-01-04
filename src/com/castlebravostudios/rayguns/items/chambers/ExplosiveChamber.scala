package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.ExplosiveEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

object ExplosiveChamber extends BaseChamber( Config.chamberExplosive ) {
  val moduleKey = "ExplosiveChamber"
  val powerModifier = 10.0
  val shotEffect = ExplosiveEffect
  setUnlocalizedName("rayguns.ExplosiveChamber")
  setTextureName("rayguns:chamber_explosive")

  register
  RecipeRegisterer.registerChamber( Tier3, this, Emitters.explosiveEmitter)

  registerSingleShotHandlers()
}