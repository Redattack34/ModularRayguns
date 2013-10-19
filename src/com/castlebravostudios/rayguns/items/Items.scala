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

object Items {

  private var registeredItems = Map[Class[_], Item]()

  def apply[T <: Item]( implicit mf : Manifest[T] ) : Item = registeredItems( mf.runtimeClass )

  def registerItems : Unit = {
    registerItem( new RayGun(4999) )
    registerItem( new BrokenGun(5000) )
    registerItem( new BasicBody(5001) )
    registerItem( new BasicChamber(5003) )

    registerItem( new BasicBattery(5101) )
    registerItem( new AdvancedBattery(5102) )
    registerItem( new UltimateBattery(5103) )
    registerItem( new InfiniteBattery(5104) )

    registerItem( new PreciseLens( 5201 ) )
    registerItem( new WideLens( 5202 ) )
    registerItem( new BeamLens( 5203 ) )
  }

  private def registerItem( item : Item ) : Unit = {
    registeredItems += ( item.getClass() -> item )
  }
}