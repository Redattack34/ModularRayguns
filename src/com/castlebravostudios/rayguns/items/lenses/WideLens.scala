package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.block.Block
import net.minecraft.item.Item

class WideLens(id : Int) extends DefaultItemLens(id) {

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