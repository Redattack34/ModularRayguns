package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.EnderEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object EnderChamber extends BaseChamber {
  val moduleKey = "EnderChamber"
  val powerModifier = 2.0
  val shotEffect = EnderEffect
  val nameSegmentKey = "rayguns.EnderChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.EnderChamber")
    .setTextureName("rayguns:chamber_ender")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}