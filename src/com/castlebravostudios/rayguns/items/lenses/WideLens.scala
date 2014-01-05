package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object WideLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "WideLens"
  val powerModifier = 3.0
  val nameSegmentKey = "rayguns.WideLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.WideLens")
    .setTextureName("rayguns:lens_wide")

  ModuleRegistry.registerModule(this)
  LensGrinderRecipeRegistry.register( 1200, new ItemStack(item),
      "IGI",
      "GDG",
      "IGI",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'D' -> Item.diamond )
  )
}