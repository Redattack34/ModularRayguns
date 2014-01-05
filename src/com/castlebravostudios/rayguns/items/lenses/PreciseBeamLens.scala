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
import com.castlebravostudios.rayguns.mod.ModularRayguns

object PreciseBeamLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "PreciseBeamLens"
  val powerModifier = 1.2
  val nameSegmentKey = "rayguns.PreciseBeamLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.PreciseBeamLens")
    .setTextureName("rayguns:lens_beam_precise")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerRecipe() : Unit =
    LensGrinderRecipeRegistry.register( 600, new ItemStack(item),
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> Block.glowStone ),
      ( 'I' -> Item.ingotIron ) )
}