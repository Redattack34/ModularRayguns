package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.HeatRayEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule


object HeatRayChamber extends BaseChamber {
  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  val shotEffect = HeatRayEffect
  val nameSegmentKey = "rayguns.HeatRayChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.HeatRayChamber")
    .setTextureName("rayguns:chamber_heat_ray")

  def registerRecipe() : Unit =
    RecipeRegisterer.registerChamber( Tier1, this, Emitters.heatRayEmitter)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}
