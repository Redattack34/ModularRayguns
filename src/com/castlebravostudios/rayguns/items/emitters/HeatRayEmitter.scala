package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object HeatRayEmitter extends Item( Config.emitterHeatRay ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.HeatRayEmitter")
  setTextureName("rayguns:emitter_heat_ray")

  RecipeRegisterer.registerTier1Emitter(this, Item.coal, Item.bucketLava)
}