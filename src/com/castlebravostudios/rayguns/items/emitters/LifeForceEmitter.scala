package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object LifeForceEmitter extends Item( Config.emitterLifeForce ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.LifeForceEmitter")
  setTextureName("rayguns:emitter_life_force")

  RecipeRegisterer.registerTier2Emitter(this, Item.speckledMelon, Item.ghastTear)
}