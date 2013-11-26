package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object LightningEmitter extends Item( Config.emitterLightning ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.LightningEmitter")
  setTextureName("rayguns:emitter_lightning")

  RecipeRegisterer.registerTier2Emitter(this, Block.blockIron, Block.blockRedstone)
}