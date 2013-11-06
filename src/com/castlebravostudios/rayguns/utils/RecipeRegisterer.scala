package com.castlebravostudios.rayguns.utils

import net.minecraft.item.Item
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.items.misc.EnergizedDiamond
import net.minecraft.block.Block
import com.castlebravostudios.rayguns.items.misc.GlassGainMedium
import com.castlebravostudios.rayguns.items.misc.GlowstoneGainMedium
import com.castlebravostudios.rayguns.items.misc.DiamondGainMedium

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

  def registerTier2Emitter( emitter : Item, item : AnyRef) : Unit =
    registerTier2Emitter( emitter, item, item, item, item)
  def registerTier2Emitter( emitter : Item, vert : AnyRef, horiz: AnyRef) : Unit =
    registerTier2Emitter( emitter, vert, horiz, vert, horiz )
  def registerTier2Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit = {
    GameRegistry.addRecipe( new ItemStack( emitter, 1 ),
      "ITI",
      "LDR",
      "IBI",
      'I' : Character, Item.ingotIron,
      'D' : Character, EnergizedDiamond,
      'T' : Character, top,
      'R' : Character, right,
      'B' : Character, bottom,
      'L' : Character, left )
  }

  def registerTier3Emitter( emitter : Item, item : AnyRef) : Unit =
    registerTier3Emitter( emitter, item, item, item, item)
  def registerTier3Emitter( emitter : Item, vert : AnyRef, horiz: AnyRef) : Unit =
    registerTier3Emitter( emitter, vert, horiz, vert, horiz )
  def registerTier3Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit = {
    GameRegistry.addRecipe( new ItemStack( emitter, 1 ),
      "ITI",
      "LDR",
      "IBI",
      'I' : Character, Item.ingotIron,
      'D' : Character, Item.netherStar,
      'T' : Character, top,
      'R' : Character, right,
      'B' : Character, bottom,
      'L' : Character, left )
  }

  def registerTier1Chamber( chamber : Item, emitter : Item ) : Unit = {
    GameRegistry.addRecipe( new ItemStack( chamber, 1 ),
      "III",
      "GGE",
      "III",
      'I' : Character, Item.ingotIron,
      'G' : Character, GlassGainMedium,
      'E' : Character, emitter )
  }

  def registerTier2Chamber( chamber : Item, emitter : Item ) : Unit = {
    GameRegistry.addRecipe( new ItemStack( chamber, 1 ),
      "GGG",
      "LLE",
      "GGG",
      'G' : Character, Item.ingotGold,
      'L' : Character, GlowstoneGainMedium,
      'E' : Character, emitter )
  }

  def registerTier3Chamber( chamber : Item, emitter : Item ) : Unit = {
    GameRegistry.addRecipe( new ItemStack( chamber, 1 ),
      "DDD",
      "LLE",
      "DDD",
      'D' : Character, Item.diamond,
      'L' : Character, DiamondGainMedium,
      'E' : Character, emitter )
  }
}