package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.api.items.ItemLens
import cpw.mods.fml.common.registry.GameRegistry
import com.castlebravostudios.rayguns.items.batteries.BasicBattery

object ChargeBeamLens extends Item( Config.chargeBeamLens ) with ItemLens {

  val moduleKey = "ChargeBeamLens"
  val powerModifier = 1.2
  register
  setUnlocalizedName("rayguns.ChargeBeamLens")

  setTextureName("rayguns:lens_charge_beam")

  GameRegistry.addShapelessRecipe( new ItemStack( this, 1 ),
    PreciseBeamLens, BasicBattery )
}