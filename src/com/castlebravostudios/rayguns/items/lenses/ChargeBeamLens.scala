package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object ChargeBeamLens extends Item( Config.chargeBeamLens ) with ItemLens {
  val moduleKey = "ChargeBeamLens"
  val powerModifier = 1.2
  val nameSegmentKey = "rayguns.ChargeBeamLens.segment"

  setUnlocalizedName("rayguns.ChargeBeamLens")
  setTextureName("rayguns:lens_charge_beam")

  ModuleRegistry.registerModule(this)
  GameRegistry.addShapelessRecipe( new ItemStack( this, 1 ),
    PreciseBeamLens, BasicBattery )
}