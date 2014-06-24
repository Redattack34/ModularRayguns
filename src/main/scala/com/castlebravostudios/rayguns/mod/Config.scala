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
import com.castlebravostudios.rayguns.items.recipes.RecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.ThermalExpansionRecipeLibrary
import com.castlebravostudios.rayguns.items.recipes.VanillaRecipeLibrary
import com.castlebravostudios.rayguns.utils.Logging
import net.minecraftforge.common.config.Configuration

object Config extends Logging {

  var basicBattery : Boolean = _
  var advancedBattery : Boolean = _
  var ultimateBattery : Boolean = _
  var infiniteBattery : Boolean = _

  var preciseLens : Boolean = _
  var wideLens : Boolean = _

  var highEfficiencyWiring : Boolean = _
  var refireCapacitor : Boolean = _
  var solarPanel : Boolean = _
  var chargeCapacitor : Boolean = _

  var mantisFrame : Boolean = _
  var fireflyFrame : Boolean = _

  var chamberLaser : Boolean = _
  var chamberHeatRay : Boolean = _
  var chamberLifeForce : Boolean = _
  var chamberFrostRay : Boolean = _
  var chamberFortifiedSunlight : Boolean = _
  var chamberExplosive : Boolean = _
  var chamberDeathRay : Boolean = _
  var chamberEnder : Boolean = _
  var chamberLightning : Boolean = _
  var chamberImpulse : Boolean = _
  var chamberTractor : Boolean = _
  var chamberCuttingTier1 : Boolean = _
  var chamberCuttingTier2 : Boolean = _
  var chamberCuttingTier3 : Boolean = _
  var chamberMatterTransporter : Boolean = _

  var barrelBeam : Boolean = _
  var barrelBlaster : Boolean = _

  var minLightningDetail : Double = _
  var lightningFlash : Boolean = _
  var rfPowerMultiplier : Double = _
  var ic2PowerMultiplier : Double = _

  var recipeLibrary : RecipeLibrary = _

  def load( file : File ): Unit = {
    val config = new Configuration( file )
    config.load()

    loadBatteryItemIds(config)
    loadLensItemIds(config)
    loadAccessoryItemIds(config)
    loadGunFrameItemIds(config)
    loadChamberItemIds(config)
    loadBarrelItemIds(config)
    loadConfigOptions( config )
    loadRecipes( config )

    if ( config.hasChanged() ) {
      config.save()
    }
  }

  private def loadBatteryItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "batteries", "Battery Modules - Set to false to disable" )

    basicBattery = range.getItemEnabled( "basicBattery" )
    advancedBattery = range.getItemEnabled( "advancedBattery" )
    ultimateBattery = range.getItemEnabled( "ultimateBattery" )
    infiniteBattery = range.getItemEnabled( "infiniteBattery" )
  }

  private def loadLensItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "lenses", "Lens Modules - Set to false to disable" )

    preciseLens = range.getItemEnabled( "preciseLens" )
    wideLens = range.getItemEnabled( "wideLens" )
  }

  private def loadAccessoryItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "accessories", "Accessory Modules - Set to false to disable" )

    highEfficiencyWiring = range.getItemEnabled( "highEfficiencyWiring" )
    refireCapacitor = range.getItemEnabled( "refireCapacitor" )
    solarPanel = range.getItemEnabled( "solarPanel" )
    chargeCapacitor = range.getItemEnabled( "chargeCapacitor" )
  }

  private def loadGunFrameItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "raygun-frames", "Raygun Frame Modules - Set to false to disable" )

    mantisFrame = range.getItemEnabled( "mantisFrame" )
    fireflyFrame = range.getItemEnabled( "fireflyFrame" )
  }

  private def loadChamberItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "beam-chambers", "Beam Chamber Modules - Set to false to disable" )

    chamberLaser = range.getItemEnabled( "chamberLaser" )
    chamberHeatRay = range.getItemEnabled( "chamberHeatRay" )
    chamberLifeForce = range.getItemEnabled( "chamberLifeForce" )
    chamberFrostRay = range.getItemEnabled( "chamberFrostRay" )
    chamberFortifiedSunlight = range.getItemEnabled( "chamberFortifiedSunlight" )
    chamberExplosive = range.getItemEnabled( "chamberExplosive" )
    chamberDeathRay = range.getItemEnabled( "chamberDeathRay" )
    chamberEnder = range.getItemEnabled( "chamberEnder" )
    chamberLightning = range.getItemEnabled( "chamberLightning" )
    chamberImpulse = range.getItemEnabled( "chamberImpulse" )
    chamberTractor = range.getItemEnabled( "chamberTractor" )
    chamberCuttingTier1 = range.getItemEnabled( "chamberCuttingTier1" )
    chamberCuttingTier2 = range.getItemEnabled( "chamberCuttingTier2" )
    chamberCuttingTier3 = range.getItemEnabled( "chamberCuttingTier3" )
    chamberMatterTransporter = range.getItemEnabled( "chamberMatterTransporter" )
  }

  private def loadBarrelItemIds( config : Configuration ) : Unit = {
    val range = new Category( config, "barrels", "Barrel Modules - Set to false to disable" )

    barrelBeam = range.getItemEnabled( "barrelBeam" )
    barrelBlaster = range.getItemEnabled( "barrelBlaster" )
  }

  private def loadConfigOptions( config : Configuration ) : Unit = {
    minLightningDetail = config.get( "config-options", "minLightningDetailSize", 0.01d ).getDouble( 0.01d )
    lightningFlash = config.get( "config-options", "lightningFlashEnabled", true ).getBoolean( true )
    rfPowerMultiplier = config.get( "config-options", "rfPowerMultiplier", 20.0d ).getDouble( 20.0d )
    ic2PowerMultiplier = config.get( "config-options", "ic2PowerMultiplier", 4.0d ).getDouble( 4.0d )
  }

  def loadRecipes(config: Configuration) : Unit = {
    val str = config.get( "config-options", "recipeLibrary", "vanilla", "Current allowed values are: vanilla, thermalExpansion" ).getString();
    str match {
      case "vanilla" => recipeLibrary = VanillaRecipeLibrary
      case "thermalExpansion" if cpw.mods.fml.common.Loader.isModLoaded("ThermalExpansion") =>
        recipeLibrary = ThermalExpansionRecipeLibrary
      case _ => {
        severe( "Either the recipe library is set to an unknown value, or the " +
            "selected library requires a mod that is not installed. Defaulting to vanilla recipes." )
        recipeLibrary = VanillaRecipeLibrary
      }
    }
  }

  private class Category( config : Configuration, name : String, comment : String ) {
    config.getCategory( name ).setComment( comment )

    def getItemEnabled( key : String ) : Boolean = config.get( name, key, true ).getBoolean( true )
  }
}