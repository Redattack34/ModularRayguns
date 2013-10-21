package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBody
import com.castlebravostudios.rayguns.api.defaults.DefaultItemLens
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config

object PreciseLens extends DefaultItemLens( Config.preciseLens ) {

  val moduleKey = "PreciseLens"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.PreciseLens")

  setTextureName("rayguns:lens_precise")

  LensGrinderRecipeRegistry.register( 600, new ItemStack(this),
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron )
  )
}