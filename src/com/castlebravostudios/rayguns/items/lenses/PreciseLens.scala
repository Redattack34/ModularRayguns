package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens

class PreciseLens(id : Int) extends DefaultItemLens(id) {

  val moduleKey = "PreciseLens"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.PreciseLens")

  setTextureName("rayguns:lens_precise")
}