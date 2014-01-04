package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.ModuleRegistry

object PreciseLens extends Item( Config.preciseLens ) with ItemLens {

  val moduleKey = "PreciseLens"
  val powerModifier = 1.5

  setUnlocalizedName("rayguns.PreciseLens")
  setTextureName("rayguns:lens_precise")

  ModuleRegistry.registerModule(this)
  LensGrinderRecipeRegistry.register( 600, new ItemStack(this),
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron )
  )
}