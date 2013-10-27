package com.castlebravostudios.rayguns.items.emitters

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs

object HeatRayEmitter extends Item( Config.emitterHeatRay ) {

  setCreativeTab(CreativeTabs.tabMaterials)
  setUnlocalizedName("rayguns.HeatRayEmitter")
  setTextureName("rayguns:emitter_heat_ray")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "ICI ",
    "FDF",
    "ICI",
    'I' : Character, Item.ingotIron,
    'F' : Character, Item.flintAndSteel,
    'C' : Character, Item.coal )
}