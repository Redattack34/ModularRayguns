package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.mod.Config

import net.minecraft.item.Item

object BeamLens extends Item( Config.beamLens ) with ItemLens {

  val moduleKey = "BeamLens"
  val powerModifier = 0.2
  register
  setUnlocalizedName("rayguns.BeamLens")

  setTextureName("rayguns:lens_beam")
}