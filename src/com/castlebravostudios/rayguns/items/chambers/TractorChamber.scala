package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.TractorEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

object TractorChamber extends BaseChamber( Config.chamberTractor ) {
  val moduleKey = "TractorChamber"
  val powerModifier = 1.5
  val shotEffect = TractorEffect

  setUnlocalizedName("rayguns.TractorChamber")
  setTextureName("rayguns:chamber_tractor")

  register
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.tractorEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}