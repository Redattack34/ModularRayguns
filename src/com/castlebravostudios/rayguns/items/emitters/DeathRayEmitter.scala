package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object DeathRayEmitter extends Item( Config.emitterDeathRay ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.DeathRayEmitter")
  setTextureName("rayguns:emitter_death_ray")

  RecipeRegisterer.registerTier3Emitter(this, new ItemStack( Item.skull, 1, 1 ) )
}