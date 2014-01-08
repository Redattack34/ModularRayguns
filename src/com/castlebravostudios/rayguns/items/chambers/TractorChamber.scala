package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.TractorEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns

object TractorChamber extends BaseChamber {
  val moduleKey = "TractorChamber"
  val powerModifier = 1.5
  val shotEffect = TractorEffect
  val nameSegmentKey = "rayguns.TractorChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.TractorChamber")
    .setTextureName("rayguns:chamber_tractor")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}