package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody

class BasicBody(id : Int) extends DefaultItemBody(id) {

  val moduleKey = "BasicBody"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.BasicBody")
}