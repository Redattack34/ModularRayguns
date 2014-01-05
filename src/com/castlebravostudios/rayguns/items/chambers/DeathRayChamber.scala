package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule

object DeathRayChamber extends BaseChamber {
  val moduleKey = "DeathRayChamber"
  val powerModifier = 5.0
  val shotEffect = DeathRayEffect
  val nameSegmentKey = "rayguns.DeathRayChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.DeathRayChamber")
    .setTextureName("rayguns:chamber_death_ray")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber(Tier3, this, Emitters.deathRayEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
}