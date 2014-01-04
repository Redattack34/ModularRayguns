package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

object FortifiedSunlightChamber extends BaseChamber( Config.chamberFortifiedSunlight ) {
  val moduleKey = "FortifiedSunlightChamber"
  val powerModifier = 4.0
  val shotEffect = FortifiedSunlightEffect

  setUnlocalizedName("rayguns.FortifiedSunlightChamber")
  setTextureName("rayguns:chamber_fortified_sunlight")

  register
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.fortifiedSunlightEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}