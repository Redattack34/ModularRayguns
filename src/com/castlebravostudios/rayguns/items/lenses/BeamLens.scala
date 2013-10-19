package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens

class BeamLens(id : Int) extends DefaultItemLens(id) {

  val moduleKey = "BeamLens"
  val powerModifier = 0.2
  register
  setUnlocalizedName("rayguns.BeamLens")

  setTextureName("rayguns:lens_beam")
}