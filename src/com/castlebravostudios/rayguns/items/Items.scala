package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.items.batteries.AdvancedBattery
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.items.batteries.InfiniteBattery
import com.castlebravostudios.rayguns.items.batteries.UltimateBattery
import com.castlebravostudios.rayguns.items.bodies.BasicBody
import com.castlebravostudios.rayguns.items.chambers.BasicChamber
import com.castlebravostudios.rayguns.items.lenses.BeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config

object Items {

  private var registeredItems = Map[Class[_], Item]()

  def apply[T <: Item]( c : Class[T] ) : Item = registeredItems( c )
  def apply[T <: Item]( implicit mf : Manifest[T] ) : Item = registeredItems( mf.runtimeClass )

  def registerItems : Unit = {
    registerItem( new RayGun( Config.item( "rayGun" ) ) )
    registerItem( new BrokenGun( Config.item( "brokenGun" ) ) )
    registerItem( new BasicBody( Config.item( "basicBody" ) ) )
    registerItem( new BasicChamber( Config.item( "basicChamber" ) ) )

    registerItem( new BasicBattery( Config.item( "basicBattery" ) ) )
    registerItem( new AdvancedBattery( Config.item( "advancedBattery" ) ) )
    registerItem( new UltimateBattery( Config.item( "ultimateBattery" ) ) )
    registerItem( new InfiniteBattery( Config.item( "infiniteBattery" ) ) )

    registerItem( new PreciseLens( Config.item( "preciseLens" ) ) )
    registerItem( new WideLens( Config.item( "wideLens" ) ) )
    registerItem( new BeamLens( Config.item( "beamLens" ) ) )
  }

  private def registerItem( item : Item ) : Unit = {
    registeredItems += ( item.getClass() -> item )
  }
}