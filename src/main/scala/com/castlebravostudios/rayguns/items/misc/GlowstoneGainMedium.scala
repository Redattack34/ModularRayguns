package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object GlowstoneGainMedium extends Item( Config.glowstoneGainMedium ) {

  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.GlowstoneGainMedium")
  setTextureName("rayguns:glowstone_gain_medium")
}