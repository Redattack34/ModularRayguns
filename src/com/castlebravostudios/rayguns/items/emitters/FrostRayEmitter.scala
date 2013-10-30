package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs

object FrostRayEmitter extends Item( Config.emitterFrostRay ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.FrostRayEmitter")
  setTextureName("rayguns:emitter_frost_ray")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "ICI ",
    "SDS",
    "ICI",
    'I' : Character, Item.ingotIron,
    'F' : Character, Block.snow,
    'C' : Character, Block.ice,
    'D' : Character, Item.diamond )
}