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

object Config {

  var rayGun : Int = _
  var brokenGun : Int = _
  var energizedDiamond : Int = _
  var glassGainMedium : Int = _
  var glowstoneGainMedium : Int = _
  var diamondGainMedium : Int = _

  var basicBattery : Int = _
  var advancedBattery : Int = _
  var ultimateBattery : Int = _
  var infiniteBattery : Int = _

  var preciseLens : Int = _
  var wideLens : Int = _
  var preciseBeamLens : Int = _
  var chargeLens : Int = _
  var chargeBeamLens : Int = _

  var extendedBattery : Int = _
  var refireCapacitor : Int = _
  var solarPanel : Int = _

  var mantisBody : Int = _
  var fireflyBody : Int = _

  var gunBench : Int = _
  var lensGrinder : Int = _
  var invisibleRedstone : Int = _

  var emitterShrinkRay : Int = _
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

  var minLightningDetail : Double = _
  var lightningFlash : Boolean = _
  var rfPowerMultiplier : Double = _
  var ic2PowerMultiplier : Double = _

  var recipeLibrary : RecipeLibrary = _

  def load( file : File ): Unit = {
    println( file.getAbsolutePath() );
    val config = new Configuration( file )
    config.load()

    loadMiscItemIds( config )
    loadBatteryItemIds(config)
    loadLensItemIds(config)
    loadAccessoryItemIds(config)
    loadGunBodyItemIds(config)
    loadEmitterItemIds(config)
    loadChamberItemIds(config)
    loadBlockIds( config )
    loadMisc( config )
    loadRecipes( config )

    if ( config.hasChanged() ) {
      config.save()
    }
  }

  //scalastyle:off magic.number

  private def loadMiscItemIds( config : Configuration ) : Unit = {
    rayGun=config.getItem( "rayGun", 4999 ).getInt()
    brokenGun=config.getItem( "brokenGun", 5000 ).getInt()
    energizedDiamond=config.getItem( "energizedDiamond", 5001 ).getInt()
    glassGainMedium=config.getItem( "glassGainMedium", 5002 ).getInt()
    glowstoneGainMedium=config.getItem( "glowstoneGainMedium", 5003 ).getInt()
    diamondGainMedium=config.getItem( "diamondGainMedium", 5004 ).getInt()
  }

  private def loadBatteryItemIds( config : Configuration ) : Unit = {
    basicBattery=config.getItem( "basicBattery", 5101 ).getInt()
    advancedBattery=config.getItem( "advancedBattery", 5102 ).getInt()
    ultimateBattery=config.getItem( "ultimateBattery", 5103 ).getInt()
    infiniteBattery=config.getItem( "infiniteBattery", 5104 ).getInt()
  }

  private def loadLensItemIds( config : Configuration ) : Unit = {
    preciseLens=config.getItem( "preciseLens", 5201 ).getInt()
    wideLens=config.getItem( "wideLens", 5202 ).getInt()
    preciseBeamLens=config.getItem( "beamLens", 5203 ).getInt()
    chargeLens=config.getItem( "chargeLens", 5204 ).getInt()
    chargeBeamLens=config.getItem( "chargeBeamLens", 5205 ).getInt()
  }

  private def loadAccessoryItemIds( config : Configuration ) : Unit = {
    extendedBattery=config.getItem( "extendedBattery", 5301 ).getInt()
    refireCapacitor=config.getItem( "refireCapacitor", 5302 ).getInt()
    solarPanel=config.getItem( "solarPanel", 5303 ).getInt()
  }

  private def loadGunBodyItemIds( config : Configuration ) : Unit = {
    mantisBody=config.getItem( "mantisBody", 5401 ).getInt
    fireflyBody=config.getItem( "fireflyBody", 5402 ).getInt
  }

  private def loadEmitterItemIds( config : Configuration ) : Unit = {
    emitterShrinkRay=config.getItem( "emitterShrinkRay", 5500 ).getInt
    emitterLaser=config.getItem( "emitterLaser", 5501 ).getInt
    emitterHeatRay=config.getItem( "emitterHeatRay", 5502 ).getInt
    emitterLifeForce=config.getItem( "emitterLifeForce", 5503 ).getInt
    emitterFrostRay=config.getItem( "emitterFrostRay", 5504 ).getInt
    emitterFortifiedSunlight=config.getItem( "emitterFortifiedSunlight", 5505 ).getInt
    emitterExplosive=config.getItem( "emitterExplosive", 5506 ).getInt
    emitterDeathRay=config.getItem( "emitterDeathRay", 5507 ).getInt
    emitterEnder=config.getItem( "emitterEnder", 5508 ).getInt
    emitterLightning=config.getItem( "emitterLightning", 5509 ).getInt
    emitterImpulse=config.getItem( "emitterImpulse", 5510 ).getInt
    emitterTractor=config.getItem( "emitterTractor", 5511 ).getInt
    emitterTier1Cutting=config.getItem( "emitterTier1Cutting", 5512 ).getInt
    emitterTier2Cutting=config.getItem( "emitterTier2Cutting", 5513 ).getInt
    emitterTier3Cutting=config.getItem( "emitterTier3Cutting", 5514 ).getInt
    emitterMatterTransporter=config.getItem( "emitterMatterTransporter", 5515 ).getInt
  }

  private def loadChamberItemIds( config : Configuration ) : Unit = {
    chamberLaser=config.getItem( "chamberLaser", 5601 ).getInt
    chamberHeatRay=config.getItem( "chamberHeatRay", 5602 ).getInt
    chamberLifeForce=config.getItem( "chamberLifeForce", 5603 ).getInt
    chamberFrostRay=config.getItem( "chamberFrostRay", 5604 ).getInt
    chamberFortifiedSunlight=config.getItem( "chamberFortifiedSunlight", 5605 ).getInt
    chamberExplosive=config.getItem( "chamberExplosive", 5606 ).getInt
    chamberDeathRay=config.getItem( "chamberDeathRay", 5607 ).getInt
    chamberEnder=config.getItem( "chamberEnder", 5608 ).getInt
    chamberLightning=config.getItem( "chamberLightning", 5609 ).getInt
    chamberImpulse=config.getItem( "chamberImpulse", 5610 ).getInt
    chamberTractor=config.getItem( "chamberTractor", 5611 ).getInt
    chamberCuttingTier1=config.getItem( "chamberCuttingTier1", 5612 ).getInt
    chamberCuttingTier2=config.getItem( "chamberCuttingTier2", 5613 ).getInt
    chamberCuttingTier3=config.getItem( "chamberCuttingTier3", 5614 ).getInt
    chamberMatterTransporter=config.getItem( "chamberMatterTransporter", 5615 ).getInt
  }

  private def loadMisc( config : Configuration ) : Unit = {
    minLightningDetail=config.get( "misc", "minLightningDetailSize", 0.01d ).getDouble( 0.01d )
    lightningFlash=config.get( "misc", "lightningFlashEnabled", true ).getBoolean( true )
    rfPowerMultiplier=config.get( "misc", "rfPowerMultiplier", 20.0d ).getDouble( 20.0d )
    ic2PowerMultiplier=config.get( "misc", "ic2PowerMultiplier", 4.0d ).getDouble( 4.0d )
  }

  private def loadBlockIds( config : Configuration ) : Unit = {
    gunBench=config.getBlock( "gunBench", 1337 ).getInt()
    lensGrinder=config.getBlock( "lensGrinder", 1338 ).getInt()
    invisibleRedstone=config.getBlock( "invisibleRedstone", 1339 ).getInt()
  }

  def loadRecipes(config: Configuration) : Unit = {
    val str = config.get( "Recipes", "recipeLibrary", "vanilla", "Current allowed values are: vanilla" );
    recipeLibrary=VanillaRecipeLibrary
  }

}