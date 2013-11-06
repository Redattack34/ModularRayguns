package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object ExplosiveEmitter extends Item( Config.emitterExplosive ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.ExplosiveEmitter")
  setTextureName("rayguns:emitter_Explosive")

  RecipeRegisterer.registerTier3Emitter(this, Block.tnt)
}