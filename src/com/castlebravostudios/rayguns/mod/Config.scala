package com.castlebravostudios.rayguns.mod

import java.io.File
import net.minecraftforge.common.Configuration
import net.minecraftforge.common.Property

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

  var extendedBattery : Int = _
  var refireCapacitor : Int = _
  var solarPanel : Int = _

  var mantisBody : Int = _
  var fireflyBody : Int = _

  var gunBench : Int = _
  var lensGrinder : Int = _

  var emitterShrinkRay : Int = _
  var emitterLaser : Int = _
  var emitterHeatRay : Int = _
  var emitterLifeForce : Int = _
  var emitterFrostRay : Int = _
  var emitterFortifiedSunlight : Int = _
  var emitterExplosive : Int = _
  var emitterDeathRay : Int = _

  var chamberLaser : Int = _
  var chamberHeatRay : Int = _
  var chamberLifeForce : Int = _
  var chamberFrostRay : Int = _
  var chamberFortifiedSunlight : Int = _
  var chamberExplosive : Int = _
  var chamberDeathRay : Int = _

  def load( file : File ) : Unit = {
    val config = new Configuration( file )
    config.load()

    loadItemIds( config )
    loadBlockIds( config )

    if ( config.hasChanged() ) {
      config.save()
    }
  }

  private def loadItemIds( config : Configuration ) : Unit = {
    rayGun = config.getItem( "rayGun", 4999 ).getInt()
    brokenGun = config.getItem( "brokenGun", 5000 ).getInt()
    energizedDiamond = config.getItem( "energizedDiamond", 5001 ).getInt()
    glassGainMedium = config.getItem( "glassGainMedium", 5002 ).getInt()
    glowstoneGainMedium = config.getItem( "glowstoneGainMedium", 5003 ).getInt()
    diamondGainMedium = config.getItem( "diamondGainMedium", 5004 ).getInt()

    basicBattery = config.getItem( "basicBattery", 5101 ).getInt()
    advancedBattery = config.getItem( "advancedBattery", 5102 ).getInt()
    ultimateBattery = config.getItem( "ultimateBattery", 5103 ).getInt()
    infiniteBattery = config.getItem( "infiniteBattery", 5104 ).getInt()

    preciseLens = config.getItem( "preciseLens", 5201 ).getInt()
    wideLens = config.getItem( "wideLens", 5202 ).getInt()
    preciseBeamLens = config.getItem( "beamLens", 5203 ).getInt()

    extendedBattery = config.getItem( "extendedBattery", 5301 ).getInt()
    refireCapacitor = config.getItem( "refireCapacitor", 5302 ).getInt()
    solarPanel = config.getItem( "solarPanel", 5303 ).getInt()

    mantisBody = config.getItem( "mantisBody", 5401 ).getInt
    fireflyBody = config.getItem( "fireflyBody", 5402 ).getInt

    emitterShrinkRay = config.getItem( "emitterShrinkRay", 5500 ).getInt
    emitterLaser = config.getItem( "emitterLaser", 5501 ).getInt
    emitterHeatRay = config.getItem( "emitterHeatRay", 5502 ).getInt
    emitterLifeForce = config.getItem( "emitterLifeForce", 5503 ).getInt
    emitterFrostRay = config.getItem( "emitterFrostRay", 5504 ).getInt
    emitterFortifiedSunlight = config.getItem( "emitterFortifiedSunlight", 5505 ).getInt
    emitterExplosive = config.getItem( "emitterExplosive", 5506 ).getInt
    emitterDeathRay = config.getItem( "emitterDeathRay", 5507 ).getInt

    chamberLaser = config.getItem( "chamberLaser", 5601 ).getInt
    chamberHeatRay = config.getItem( "chamberHeatRay", 5602 ).getInt
    chamberLifeForce = config.getItem( "chamberLifeForce", 5603 ).getInt
    chamberFrostRay = config.getItem( "chamberFrostRay", 5604 ).getInt
    chamberFortifiedSunlight = config.getItem( "chamberFortifiedSunlight", 5605 ).getInt
    chamberExplosive = config.getItem( "chamberExplosive", 5606 ).getInt
    chamberDeathRay = config.getItem( "chamberDeathRay", 5607 ).getInt
  }

  private def loadBlockIds( config : Configuration ) : Unit = {
    gunBench = config.getBlock( "gunBench", 1337 ).getInt()
    lensGrinder = config.getBlock( "lensGrinder", 1338 ).getInt()
  }
}