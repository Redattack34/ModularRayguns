package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object PreciseLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "PreciseLens"
  val powerModifier = 1.5
  val nameSegmentKey = "rayguns.PreciseLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.PreciseLens")
    .setTextureName("rayguns:lens_precise")

  ModuleRegistry.registerModule(this)
  LensGrinderRecipeRegistry.register( 600, new ItemStack(item),
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron )
  )
}