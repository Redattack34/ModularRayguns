package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object FortifiedSunlightEmitter extends Item( Config.emitterFortifiedSunlight ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.SunlightEmitter")
  setTextureName("rayguns:emitter_fortified_sunlight")

  RecipeRegisterer.registerTier2Emitter(this, Block.wood)
}