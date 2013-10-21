package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.mod.Config

object BasicBody extends DefaultItemBody( Config.basicBody ) {

  val moduleKey = "BasicBody"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.BasicBody")
}