package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.FrostRayEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule


object FrostRayChamber extends BaseChamber {
  val moduleKey = "FrostRayChamber"
  val powerModifier = 2.0
  val shotEffect = FrostRayEffect
  val nameSegmentKey = "rayguns.FrostRayChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.FrostRayChamber")
    .setTextureName("rayguns:chamber_frost_ray")

  def registerRecipe() : Unit =
    RecipeRegisterer.registerChamber( Tier2, this, Emitters.frostRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
