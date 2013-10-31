package com.castlebravostudios.rayguns.items.misc

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object EnergizedDiamond extends Item( Config.energizedDiamond ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.EnergizedDiamond")
  setTextureName("rayguns:energized_diamond")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "GRG",
    "RDR",
    "GRG",
    'G' : Character, Block.glowStone,
    'R' : Character, Block.blockRedstone,
    'D' : Character, Item.diamond )

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "RGR",
    "GFG",
    "RGR",
    'G' : Character, Block.glowStone,
    'R' : Character, Block.blockRedstone,
    'D' : Character, Item.diamond )
}