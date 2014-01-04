package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.entities.effects.LaserEffect
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.items.emitters.Emitters

import net.minecraft.item.Item

import net.minecraft.item.Item

object LaserChamber extends BaseChamber( Config.chamberLaser ) {
  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  val shotEffect = LaserEffect

  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  register
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.laserEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}