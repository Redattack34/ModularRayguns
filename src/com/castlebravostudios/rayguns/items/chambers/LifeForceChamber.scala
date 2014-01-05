package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.LifeForceEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.mod.ModularRayguns


object LifeForceChamber extends BaseChamber {
  val moduleKey = "LifeForceChamber"
  val powerModifier = 3.0
  val shotEffect = LifeForceEffect
  val nameSegmentKey = "rayguns.LifeForceChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.LifeForceChamber")
    .setTextureName("rayguns:chamber_life_force")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerRecipe() : Unit =
    RecipeRegisterer.registerChamber( Tier2, this, Emitters.lifeForceEmitter)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}
