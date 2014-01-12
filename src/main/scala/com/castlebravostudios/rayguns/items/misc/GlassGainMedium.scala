package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object GlassGainMedium extends Item( Config.glassGainMedium ) {

  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.GlassGainMedium")
  setTextureName("rayguns:glass_gain_medium")
}