package com.castlebravostudios.rayguns.plugins.nei

import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.items.Blocks
import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.bodies._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import com.castlebravostudios.rayguns.mod.ModularRayguns
import codechicken.nei.MultiItemRange
import codechicken.nei.api.API
import codechicken.nei.api.IConfigureNEI
import cpw.mods.fml.common.Mod
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui
import codechicken.nei.recipe.DefaultOverlayHandler

class NEIModularRaygunsConfig extends IConfigureNEI {
  import NEIModularRaygunsConfig.recipeKey

  def loadConfig() : Unit = {
    API.hideItem( Blocks.invisibleRedstone.blockID )
    API.hideItem( RayGun.itemID )
    API.hideItem( BrokenGun.itemID )

    API.addSetRange("Modular Rayguns", mainItemRange)
    API.addSetRange("Modular Rayguns.Chambers", chamberItemRange)
    API.addSetRange("Modular Rayguns.Lenses", lensItemRange)
    API.addSetRange("Modular Rayguns.Bodies", bodyItemRange)
    API.addSetRange("Modular Rayguns.Accessories", accessoryItemRange)
    API.addSetRange("Modular Rayguns.Batteries", batteryItemRange)
    API.addSetRange("Modular Rayguns.Emitters", emitterItemRange)

    API.registerRecipeHandler( new NEILensGrinderRecipeManager )
    API.registerUsageHandler( new NEILensGrinderRecipeManager )
    API.registerGuiOverlay( classOf[LensGrinderGui], recipeKey )
    API.registerGuiOverlayHandler( classOf[LensGrinderGui], new DefaultOverlayHandler, recipeKey )
  }

  def getName = ModularRayguns.getClass().getAnnotation(classOf[Mod]).name()
  def getVersion = ModularRayguns.getClass().getAnnotation(classOf[Mod]).version()

  private def mainItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    range.add(Blocks.gunBench)
    range.add(Blocks.lensGrinder)
    range.add(EnergizedDiamond)
    range.add(GlassGainMedium)
    range.add(GlowstoneGainMedium)
    range.add(DiamondGainMedium)
    range
  }

  private def chamberItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, Tier1CuttingChamber )
    addModule( range, Tier2CuttingChamber )
    addModule( range, Tier3CuttingChamber )
    addModule( range, DeathRayChamber )
    addModule( range, EnderChamber )
    addModule( range, ExplosiveChamber )
    addModule( range, FortifiedSunlightChamber )
    addModule( range, FrostRayChamber )
    addModule( range, HeatRayChamber )
    addModule( range, ImpulseChamber )
    addModule( range, LaserChamber )
    addModule( range, LifeForceChamber )
    addModule( range, LightningChamber )
    addModule( range, TractorChamber )
    range
  }

  private def lensItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, ChargeBeamLens )
    addModule( range, ChargeLens )
    addModule( range, PreciseBeamLens )
    addModule( range, PreciseLens )
    addModule( range, WideLens )
    range
  }

  private def bodyItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, FireflyBody )
    addModule( range, MantisBody )
    range
  }

  private def accessoryItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, ExtendedBattery )
    addModule( range, RefireCapacitor )
    addModule( range, SolarPanel )
    range
  }

  private def batteryItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, BasicBattery )
    addModule( range, AdvancedBattery )
    addModule( range, UltimateBattery )
    addModule( range, InfiniteBattery )
    range
  }

  private def emitterItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    range.add( Emitters.tier1CuttingEmitter )
    range.add( Emitters.tier2CuttingEmitter )
    range.add( Emitters.tier3CuttingEmitter )
    range.add( Emitters.deathRayEmitter )
    range.add( Emitters.enderEmitter )
    range.add( Emitters.explosiveEmitter )
    range.add( Emitters.fortifiedSunlightEmitter )
    range.add( Emitters.frostRayEmitter )
    range.add( Emitters.heatRayEmitter )
    range.add( Emitters.impulseEmitter )
    range.add( Emitters.laserEmitter )
    range.add( Emitters.lifeForceEmitter )
    range.add( Emitters.lightningEmitter )
    range.add( Emitters.tractorEmitter )
    range
  }

  private def addModule( range: MultiItemRange, module : RaygunModule ) = {
    if ( module.item != null ) {
      range.add(module.item)
    }
  }
}
object NEIModularRaygunsConfig {
  val recipeKey = "LensGrinder"
}