package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.EnderEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule

object EnderChamber extends BaseChamber {
  val moduleKey = "EnderChamber"
  val powerModifier = 2.0
  val shotEffect = EnderEffect
  val nameSegmentKey = "rayguns.EnderChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.EnderChamber")
    .setTextureName("rayguns:chamber_ender")

  ModuleRegistry.registerModule(this)
  def registerRecipe() : Unit =
    RecipeRegisterer.registerChamber(Tier2, this, Emitters.enderEmitter )

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}