package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.ExplosiveEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule

object ExplosiveChamber extends BaseChamber {
  val moduleKey = "ExplosiveChamber"
  val powerModifier = 10.0
  val shotEffect = ExplosiveEffect
  val nameSegmentKey = "rayguns.ExplosiveChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ExplosiveChamber")
    .setTextureName("rayguns:chamber_explosive")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier3, this, Emitters.explosiveEmitter)

  registerSingleShotHandlers()
}