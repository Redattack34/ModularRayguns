package com.castlebravostudios.rayguns.utils

import net.minecraft.item.Item
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack

object RecipeRegisterer {

  def registerTier1Emitter( emitter : Item, item : AnyRef) : Unit =
    registerTier1Emitter( emitter, item, item, item, item)
  def registerTier1Emitter( emitter : Item, vert : AnyRef, horiz: AnyRef) : Unit =
    registerTier1Emitter( emitter, vert, horiz, vert, horiz )
  def registerTier1Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit = {
   GameRegistry.addRecipe( new ItemStack( emitter, 1 ),
    "ITI",
    "LDR",
    "IBI",
    'I' : Character, Item.ingotIron,
    'D' : Character, Item.diamond,
    'T' : Character, top,
    'R' : Character, right,
    'B' : Character, bottom,
    'L' : Character, left )
  }
}