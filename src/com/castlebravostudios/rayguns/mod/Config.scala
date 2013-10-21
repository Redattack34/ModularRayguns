package com.castlebravostudios.rayguns.mod

import java.io.File
import net.minecraftforge.common.Configuration
import net.minecraftforge.common.Property

object Config {

  var rayGun : Int = _
  var brokenGun : Int = _
  var basicBody : Int = _
  var basicChamber : Int = _

  var basicBattery : Int = _
  var advancedBattery : Int = _
  var ultimateBattery : Int = _
  var infiniteBattery : Int = _

  var preciseLens : Int = _
  var wideLens : Int = _
  var beamLens : Int = _

  var gunBench : Int = _
  var lensGrinder : Int = _

  def load( file : File ) : Unit = {
    val config = new Configuration( file )
    config.load()

    loadItemIds( config )
    loadBlockIds( config )

    config.save()
  }

  private def loadItemIds( config : Configuration ) : Unit = {
    rayGun = config.getItem( "rayGun", 4999 ).getInt()
    brokenGun = config.getItem( "brokenGun", 5000 ).getInt()
    basicBody = config.getItem( "basicBody", 5001 ).getInt()
    basicChamber = config.getItem( "basicChamber", 5003 ).getInt()

    basicBattery = config.getItem( "basicBattery", 5101 ).getInt()
    advancedBattery = config.getItem( "advancedBattery", 5102 ).getInt()
    ultimateBattery = config.getItem( "ultimateBattery", 5103 ).getInt()
    infiniteBattery = config.getItem( "infiniteBattery", 5104 ).getInt()

    preciseLens = config.getItem( "preciseLens", 5201 ).getInt()
    wideLens = config.getItem( "wideLens", 5202 ).getInt()
    beamLens = config.getItem( "beamLens", 5203 ).getInt()
  }

  private def loadBlockIds( config : Configuration ) : Unit = {
    gunBench = config.getBlock( "gunBench", 1337 ).getInt()
    lensGrinder = config.getBlock( "lensGrinder", 1338 ).getInt()
  }
}