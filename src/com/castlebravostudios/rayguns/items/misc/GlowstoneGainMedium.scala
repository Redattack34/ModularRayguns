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
import com.castlebravostudios.rayguns.mod.ModularRayguns

object GlowstoneGainMedium extends Item( Config.glowstoneGainMedium ) {

  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.GlowstoneGainMedium")
  setTextureName("rayguns:glowstone_gain_medium")

  LensGrinderRecipeRegistry.register(600, new ItemStack( this, 1 ),
      " G ",
      "GGG",
      " G ",
      ('G' -> Block.glowStone ) )
}