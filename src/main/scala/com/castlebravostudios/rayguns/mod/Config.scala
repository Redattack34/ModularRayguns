/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.mod

import java.io.File
import net.minecraftforge.common.Configuration
import net.minecraftforge.common.Property
import com.castlebravostudios.rayguns.items.recipes.RecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.VanillaRecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.Ic2RecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.ThermalExpansionRecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.VanillaRecipeLibrary
import com.castlebravostudios.rayguns.utils.Logging
import com.castlebravostudios.rayguns.items.recipes.VanillaRecipeLibrary

object Config extends Logging {

  var rayGun : Int = _
  var brokenGun : Int = _
  var opticalGlass : Int = _
  var redstoneDustedGlass : Int = _
  var glowstoneDustedGlass : Int = _
  var redstoneDopedGlass : Int = _
  var glowstoneDopedGlass : Int = _
  var tier1Diode : Int = _
  var tier2Diode : Int = _
  var tier3Diode : Int = _
  var tier1HeatSink : Int = _
  var tier2HeatSink : Int = _
  var tier3HeatSink : Int = _
  var tier1Casing: Int = _
  var tier2Casing: Int = _
  var tier3Casing: Int = _
  var radiantDust: Int = _
  var radiantDustedGlass : Int = _
  var radiantDopedGlass : Int = _
  var shutter : Int = _
  var tier1GainMedium: Int = _
  var tier2GainMedium: Int = _
  var tier3GainMedium: Int = _
  var leadDustedGlass : Int = _

  var basicBattery : Int = _
  var advancedBattery : Int = _
  var ultimateBattery : Int = _
  var infiniteBattery : Int = _

  var preciseLens : Int = _
  var wideLens : Int = _

  var highEfficiencyWiring : Int = _
  var refireCapacitor : Int = _
  var solarPanel : Int = _
  var chargeCapacitor : Int = _

  var mantisFrame : Int = _
  var fireflyFrame : Int = _

  var gunBench : Int = _
  var lensGrinder : Int = _
  var invisibleRedstone : Int = _

  var emitterLaser : Int = _
  var emitterHeatRay : Int = _
  var emitterLifeForce : Int = _
  var emitterFrostRay : Int = _
  var emitterFortifiedSunlight : Int = _
  var emitterExplosive : Int = _
  var emitterDeathRay : Int = _
  var emitterEnder : Int = _
  var emitterLightning : Int = _
  var emitterImpulse : Int = _
  var emitterTractor : Int = _
  var emitterTier1Cutting : Int = _
  var emitterTier2Cutting : Int = _
  var emitterTier3Cutting : Int = _
  var emitterMatterTransporter : Int = _

  var chamberLaser : Int = _
  var chamberHeatRay : Int = _
  var chamberLifeForce : Int = _
  var chamberFrostRay : Int = _
  var chamberFortifiedSunlight : Int = _
  var chamberExplosive : Int = _
  var chamberDeathRay : Int = _
  var chamberEnder : Int = _
  var chamberLightning : Int = _
  var chamberImpulse : Int = _
  var chamberTractor : Int = _
  var chamberCuttingTier1 : Int = _
  var chamberCuttingTier2 : Int = _
  var chamberCuttingTier3 : Int = _
  var chamberMatterTransporter : Int = _

  var barrelBeam : Int = _
  var barrelBlaster : Int = _

  var minLightningDetail : Double = _
  var lightningFlash : Boolean = _
  var rfPowerMultiplier : Double = _
  var ic2PowerMultiplier : Double = _

  var recipeLibrary : RecipeLibrary = _

  def load( file : File ): Unit = {
    val config = new Configuration( file )
    config.load()

    loadMiscItemIds( config )
    loadBatteryItemIds(config)
    loadLensItemIds(config)
    loadAccessoryItemIds(config)
    loadGunFrameItemIds(config)
    loadEmitterItemIds(config)
    loadChamberItemIds(config)
    loadBarrelItemIds(config)
    loadBlockIds( config )
    loadConfigOptions( config )
    loadRecipes( config )

    if ( config.hasChanged() ) {
      config.save()
    }
  }

  //scalastyle:off magic.number

  private def loadMiscItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5000, "misc-items", "Crafting components and other items" )

    rayGun = range.getItem( "rayGun" )
    brokenGun = range.getItem( "brokenGun" )
    opticalGlass = range.getItem( "opticalGlass" )
    redstoneDustedGlass = range.getItem( "redstoneDustedGlass" )
    glowstoneDustedGlass = range.getItem( "glowstoneDustedGlass" )
    redstoneDopedGlass = range.getItem( "redstoneDopedGlass" )
    glowstoneDopedGlass = range.getItem( "glowstoneDopedGlass" )
    tier1Diode = range.getItem( "basicDiode" )
    tier2Diode = range.getItem( "advancedDiode" )
    tier3Diode = range.getItem( "ultimateDiode" )
    tier1HeatSink = range.getItem( "basicHeatSink" )
    tier2HeatSink = range.getItem( "advancedHeatSink" )
    tier3HeatSink = range.getItem( "ultimateHeatSink" )
    tier1Casing = range.getItem( "basicCasing" )
    tier2Casing = range.getItem( "advancedCasing" )
    tier3Casing = range.getItem( "ultimateCasing" )
    radiantDust = range.getItem( "radiantDust" )
    radiantDustedGlass = range.getItem( "radiantDustedGlass" )
    radiantDopedGlass = range.getItem( "radiantDopedGlass" )
    shutter = range.getItem( "shutter" )
    tier1GainMedium = range.getItem( "basicGainMedium" )
    tier2GainMedium = range.getItem( "advancedGainMedium" )
    tier3GainMedium = range.getItem( "ultimateGainMedium" )
    leadDustedGlass = range.getItem( "leadDustedGlass" )
  }

  private def loadBatteryItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5100, "batteries", "Battery Modules - Set to 0 to disable" )

    basicBattery = range.getItem( "basicBattery" )
    advancedBattery = range.getItem( "advancedBattery" )
    ultimateBattery = range.getItem( "ultimateBattery" )
    infiniteBattery = range.getItem( "infiniteBattery" )
  }

  private def loadLensItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5200, "lenses", "Lens Modules - Set to 0 to disable" )

    preciseLens = range.getItem( "preciseLens" )
    wideLens = range.getItem( "wideLens" )
  }

  private def loadAccessoryItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5300, "accessories", "Accessory Modules - Set to 0 to disable" )

    highEfficiencyWiring = range.getItem( "highEfficiencyWiring" )
    refireCapacitor = range.getItem( "refireCapacitor" )
    solarPanel = range.getItem( "solarPanel" )
    chargeCapacitor = range.getItem( "chargeCapacitor" )
  }

  private def loadGunFrameItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5400, "raygun-frames", "Raygun Frame Modules - Set to 0 to disable" )

    mantisFrame = range.getItem( "mantisFrame" )
    fireflyFrame = range.getItem( "fireflyFrame" )
  }

  private def loadEmitterItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5500, "emitters", "Raygun Beam Emitters" )

    emitterLaser = range.getItem( "emitterLaser" )
    emitterHeatRay = range.getItem( "emitterHeatRay" )
    emitterLifeForce = range.getItem( "emitterLifeForce" )
    emitterFrostRay = range.getItem( "emitterFrostRay" )
    emitterFortifiedSunlight = range.getItem( "emitterFortifiedSunlight" )
    emitterExplosive = range.getItem( "emitterExplosive" )
    emitterDeathRay = range.getItem( "emitterDeathRay" )
    emitterEnder = range.getItem( "emitterEnder" )
    emitterLightning = range.getItem( "emitterLightning" )
    emitterImpulse = range.getItem( "emitterImpulse" )
    emitterTractor = range.getItem( "emitterTractor" )
    emitterTier1Cutting = range.getItem( "emitterTier1Cutting" )
    emitterTier2Cutting = range.getItem( "emitterTier2Cutting" )
    emitterTier3Cutting = range.getItem( "emitterTier3Cutting" )
    emitterMatterTransporter = range.getItem( "emitterMatterTransporter" )
  }

  private def loadChamberItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5600, "beam-chambers", "Beam Chamber Modules - Set to 0 to disable" )

    chamberLaser = range.getItem( "chamberLaser" )
    chamberHeatRay = range.getItem( "chamberHeatRay" )
    chamberLifeForce = range.getItem( "chamberLifeForce" )
    chamberFrostRay = range.getItem( "chamberFrostRay" )
    chamberFortifiedSunlight = range.getItem( "chamberFortifiedSunlight" )
    chamberExplosive = range.getItem( "chamberExplosive" )
    chamberDeathRay = range.getItem( "chamberDeathRay" )
    chamberEnder = range.getItem( "chamberEnder" )
    chamberLightning = range.getItem( "chamberLightning" )
    chamberImpulse = range.getItem( "chamberImpulse" )
    chamberTractor = range.getItem( "chamberTractor" )
    chamberCuttingTier1 = range.getItem( "chamberCuttingTier1" )
    chamberCuttingTier2 = range.getItem( "chamberCuttingTier2" )
    chamberCuttingTier3 = range.getItem( "chamberCuttingTier3" )
    chamberMatterTransporter = range.getItem( "chamberMatterTransporter" )
  }

  private def loadBarrelItemIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 5700, "barrels", "Barrel Modules - Set to 0 to disable" )

    barrelBeam = range.getItem( "barrelBeam" )
    barrelBlaster = range.getItem( "barrelBlaster" )
  }

  private def loadConfigOptions( config : Configuration ) : Unit = {
    minLightningDetail = config.get( "config-options", "minLightningDetailSize", 0.01d ).getDouble( 0.01d )
    lightningFlash = config.get( "config-options", "lightningFlashEnabled", true ).getBoolean( true )
    rfPowerMultiplier = config.get( "config-options", "rfPowerMultiplier", 20.0d ).getDouble( 20.0d )
    ic2PowerMultiplier = config.get( "config-options", "ic2PowerMultiplier", 4.0d ).getDouble( 4.0d )
  }

  private def loadBlockIds( config : Configuration ) : Unit = {
    val range = new IdRange( config, 1000, "blocks", "" )

    gunBench = range.getBlock( "gunBench" )
    lensGrinder = range.getBlock( "lensGrinder" )
    invisibleRedstone = range.getBlock( "invisibleRedstone" )
  }

  def loadRecipes(config: Configuration) : Unit = {
    val str = config.get( "config-options", "recipeLibrary", "vanilla", "Current allowed values are: vanilla, ic2, thermalExpansion" ).getString();
    str match {
      case "vanilla" => recipeLibrary = VanillaRecipeLibrary
      case "ic2" if cpw.mods.fml.common.Loader.isModLoaded("IC2") =>
        recipeLibrary = Ic2RecipeLibrary
      case "thermalExpansion" if cpw.mods.fml.common.Loader.isModLoaded("ThermalExpansion") =>
        recipeLibrary = ThermalExpansionRecipeLibrary
      case _ => {
        severe( "Either the recipe library is set to an unknown value, or the " +
            "selected library requires a mod that is not installed. Defaulting to vanilla recipes." )
        recipeLibrary = VanillaRecipeLibrary
      }
    }
  }

  private class IdRange( config : Configuration, start: Int, name : String, comment : String ) {
    private var nextId = start
    config.getCategory( name ).setComment( comment )

    def getBlock( key : String ) : Int = config.getBlock( name, key, getNextId() ).getInt
    def getItem( key : String ) : Int = config.getItem( name, key, getNextId() ).getInt

    private def getNextId() : Int = {
      val id = nextId
      nextId += 1
      id
    }
  }
}