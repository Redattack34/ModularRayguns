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
import com.castlebravostudios.rayguns.items.accessories.ExtendedBattery
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor

object Items {

  private var registeredItems = Map[Class[_], Item]()

  def apply[T <: Item]( c : Class[T] ) : Item = registeredItems( c )
  def apply[T <: Item]( implicit mf : Manifest[T] ) : Item = registeredItems( mf.runtimeClass )

  def registerItems : Unit = {
    registerItem( new RayGun( Config.rayGun ) )
    registerItem( new BrokenGun( Config.brokenGun ) )
    registerItem( BasicBody )
    registerItem( BasicChamber )

    registerItem( BasicBattery )
    registerItem( AdvancedBattery )
    registerItem( UltimateBattery )
    registerItem( InfiniteBattery )

    registerItem( PreciseLens )
    registerItem( WideLens )
    registerItem( BeamLens )

    registerItem( ExtendedBattery )
    registerItem( RefireCapacitor )
  }

  private def registerItem( item : Item ) : Unit = {
    registeredItems += ( item.getClass() -> item )
  }
}