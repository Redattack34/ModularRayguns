package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens

class WideLens(id : Int) extends DefaultItemLens(id) {

  val moduleKey = "WideLens"
  val powerModifier = 3.0
  register
  setUnlocalizedName("rayguns.WideLens")

  setTextureName("rayguns:lens_wide")
}