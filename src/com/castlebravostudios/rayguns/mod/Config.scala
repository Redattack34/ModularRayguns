package com.castlebravostudios.rayguns.mod

import java.io.File
import net.minecraftforge.common.Configuration
import net.minecraftforge.common.Property

object Config {

  private var itemIds = Map[String, Int]()
  private var blockIds = Map[String, Int]()

  def item( key : String ) = itemIds(key)
  def block( key : String ) = blockIds(key)

  def load( file : File ) : Unit = {
    val config = new Configuration( file )
    config.load()

    loadItemIds( config )
    loadBlockIds( config )

    config.save()
  }

  private def loadItemIds( config : Configuration ) : Unit = {
    def add( p : Property ) = itemIds += ( p.getName() -> p.getInt() )

    add( config.getItem( "rayGun", 4999) )
    add( config.getItem( "brokenGun", 5000) )
    add( config.getItem( "basicBody", 5001) )
    add( config.getItem( "basicChamber", 5003) )

    add( config.getItem( "basicBattery", 5101) )
    add( config.getItem( "advancedBattery", 5102) )
    add( config.getItem( "ultimateBattery", 5103) )
    add( config.getItem( "infiniteBattery", 5104) )

    add( config.getItem( "preciseLens", 5201 ) )
    add( config.getItem( "wideLens", 5202 ) )
    add( config.getItem( "beamLens", 5203 ) )
  }

  private def loadBlockIds( config : Configuration ) : Unit = {
    def add( p : Property ) = blockIds += ( p.getName() -> p.getInt() )

    add( config.getBlock( "gunBench", 1337 ) )
    add( config.getBlock( "lensGrinder", 1338 ) )
  }
}