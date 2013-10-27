package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs

object LifeForceEmitter extends Item( Config.emitterLifeForce ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.LifeForceEmitter")
  setTextureName("rayguns:emitter_life_force")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "IMI ",
    "TDT",
    "IMI",
    'I' : Character, Item.ingotIron,
    'T' : Character, Item.ghastTear,
    'M' : Character, Item.speckledMelon,
    'D' : Character, Item.diamond )
}