package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.entities.effects.CuttingEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.api.items.ItemModule

object Tier1CuttingChamber extends BaseChamber {
  val moduleKey = "Tier1CuttingChamber"
  val powerModifier = 2.0
  val shotEffect = new CuttingEffect( "Tier1Cutting", 1, 3.0f )
  val nameSegmentKey = "rayguns.Tier1CuttingChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.Tier1CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t1")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.tier1CuttingEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
object Tier2CuttingChamber extends BaseChamber {
  val moduleKey = "Tier2CuttingChamber"
  val powerModifier = 4.0
  val shotEffect = new CuttingEffect( "Tier2Cutting", 2, 4.5f )
  val nameSegmentKey = "rayguns.Tier2CuttingChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.Tier2CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t2")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier2, this, Emitters.tier2CuttingEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}
object Tier3CuttingChamber extends BaseChamber {
  val moduleKey = "Tier3CuttingChamber"
  val powerModifier = 6.0
  val shotEffect = new CuttingEffect( "Tier3Cutting", 3, 6.0f )
  val nameSegmentKey = "rayguns.Tier3CuttingChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.Tier3CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t3")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier3, this, Emitters.tier3CuttingEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()
}