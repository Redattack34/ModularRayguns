package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.mod.Config

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object PreciseBeamLens extends Item( Config.preciseBeamLens ) with ItemLens {
  val moduleKey = "PreciseBeamLens"
  val powerModifier = 1.2
  val nameSegmentKey = "rayguns.PreciseBeamLens.segment"

  setUnlocalizedName("rayguns.PreciseBeamLens")
  setTextureName("rayguns:lens_beam_precise")

  ModuleRegistry.registerModule(this)
  LensGrinderRecipeRegistry.register( 600, new ItemStack(this),
    "IGI",
    "GGG",
    "IGI",
    ( 'G' -> Block.glowStone ),
    ( 'I' -> Item.ingotIron )
  )
}