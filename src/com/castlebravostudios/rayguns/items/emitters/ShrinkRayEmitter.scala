package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object ShrinkRayEmitter extends Item( Config.emitterShrinkRay ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.ShrinkRayEmitter")
  setTextureName("rayguns:emitter_shrink_ray")

  RecipeRegisterer.registerTier1Emitter(this, Block.pistonBase)
}