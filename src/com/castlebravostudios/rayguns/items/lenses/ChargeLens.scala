package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object ChargeLens extends Item( Config.chargeLens ) with ItemLens {
  val moduleKey = "ChargeLens"
  val powerModifier = 1.5
  val nameSegmentKey = "rayguns.ChargeLens.segment"

  setUnlocalizedName("rayguns.ChargeLens")
  setTextureName("rayguns:lens_charge")

  ModuleRegistry.registerModule(this)
  GameRegistry.addShapelessRecipe( new ItemStack( this, 1 ),
    PreciseLens, BasicBattery )
}