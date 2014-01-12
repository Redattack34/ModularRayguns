package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object DiamondGainMedium extends Item( Config.diamondGainMedium ) {

  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.DiamondGainMedium")
  setTextureName("rayguns:diamond_gain_medium")
}