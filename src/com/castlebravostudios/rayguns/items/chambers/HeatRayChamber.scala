package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.HeatRayEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns


object HeatRayChamber extends BaseChamber {
  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  val shotEffect = HeatRayEffect
  val nameSegmentKey = "rayguns.HeatRayChamber.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.HeatRayChamber")
    .setTextureName("rayguns:chamber_heat_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
  }
}
