package com.castlebravostudios.rayguns.items.misc

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry

object DiamondGainMedium extends Item( Config.diamondGainMedium ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.DiamondGainMedium")
  setTextureName("rayguns:diamond_gain_medium")

  LensGrinderRecipeRegistry.register(1200, new ItemStack( this, 1 ),
      " D ",
      "DDD",
      " D ",
      ('D' -> Item.diamond ) )
}