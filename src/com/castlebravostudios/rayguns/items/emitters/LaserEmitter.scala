package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs

object LaserEmitter extends Item( Config.emitterLaser ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.LaserEmitter")
  setTextureName("rayguns:emitter_laser")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "IRI ",
    "RDR",
    "IRI",
    'I' : Character, Item.ingotIron,
    'R' : Character, Item.redstone,
    'D' : Character, Item.diamond )
}