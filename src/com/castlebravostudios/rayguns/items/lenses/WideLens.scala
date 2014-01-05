package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.mod.Config

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object WideLens extends Item( Config.wideLens ) with ItemLens {
  val moduleKey = "WideLens"
  val powerModifier = 3.0
  val nameSegmentKey = "rayguns.WideLens.segment"

  setUnlocalizedName("rayguns.WideLens")
  setTextureName("rayguns:lens_wide")

  ModuleRegistry.registerModule(this)
  LensGrinderRecipeRegistry.register( 1200, new ItemStack(this),
      "IGI",
      "GDG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'D' -> Item.diamond )
  )
}