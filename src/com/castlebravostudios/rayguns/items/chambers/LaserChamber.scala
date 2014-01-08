package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.LaserEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object LaserChamber extends BaseChamber {
  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  val shotEffect = LaserEffect
  val nameSegmentKey = "rayguns.LaserChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.LaserChamber")
    .setTextureName("rayguns:chamber_laser")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}