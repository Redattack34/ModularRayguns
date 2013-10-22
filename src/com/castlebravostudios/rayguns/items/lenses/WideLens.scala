package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.block.Block
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.api.items.ItemLens

object WideLens extends Item( Config.wideLens ) with ItemLens {

  val moduleKey = "WideLens"
  val powerModifier = 3.0
  register
  setUnlocalizedName("rayguns.WideLens")

  setTextureName("rayguns:lens_wide")

  LensGrinderRecipeRegistry.register( 1200, new ItemStack(this),
      "IGI",
      "GDG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'D' -> Item.diamond )
  )
}