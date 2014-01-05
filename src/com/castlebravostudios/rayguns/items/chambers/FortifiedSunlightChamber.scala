package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule

object FortifiedSunlightChamber extends BaseChamber {
  val moduleKey = "FortifiedSunlightChamber"
  val powerModifier = 4.0
  val shotEffect = FortifiedSunlightEffect
  val nameSegmentKey = "rayguns.FortifiedSunlightChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.FortifiedSunlightChamber")
    .setTextureName("rayguns:chamber_fortified_sunlight")

    def registerRecipe() : Unit =
    RecipeRegisterer.registerChamber( Tier2, this, Emitters.fortifiedSunlightEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}