package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.EnderEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

object EnderChamber extends BaseChamber( Config.chamberEnder ) {
  val moduleKey = "EnderChamber"
  val powerModifier = 2.0
  val shotEffect = EnderEffect
  setUnlocalizedName("rayguns.EnderChamber")
  setTextureName("rayguns:chamber_ender")

  register
  RecipeRegisterer.registerChamber(Tier2, this, Emitters.enderEmitter )

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}