package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.block.Block

object PreciseBeamLens extends Item( Config.preciseBeamLens ) with ItemLens {

  val moduleKey = "PreciseBeamLens"
  val powerModifier = 1.2
  register
  setUnlocalizedName("rayguns.PreciseBeamLens")

  setTextureName("rayguns:lens_beam_precise")

  LensGrinderRecipeRegistry.register( 600, new ItemStack(this),
    "IGI",
    "GGG",
    "IGI",
    ( 'G' -> Block.glowStone ),
    ( 'I' -> Item.ingotIron )
  )
}