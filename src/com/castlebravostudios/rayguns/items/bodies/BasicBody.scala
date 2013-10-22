package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.mod.Config

import net.minecraft.item.Item

object BasicBody extends Item( Config.basicBody ) with ItemBody {

  val moduleKey = "BasicBody"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.BasicBody")
}