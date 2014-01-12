package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object EnergizedDiamond extends Item( Config.energizedDiamond ) {

  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.EnergizedDiamond")
  setTextureName("rayguns:energized_diamond")
}