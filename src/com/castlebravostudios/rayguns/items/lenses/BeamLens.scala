package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens
import com.castlebravostudios.rayguns.mod.Config

object BeamLens extends DefaultItemLens( Config.item( "beamLens" ) ) {

  val moduleKey = "BeamLens"
  val powerModifier = 0.2
  register
  setUnlocalizedName("rayguns.BeamLens")

  setTextureName("rayguns:lens_beam")
}