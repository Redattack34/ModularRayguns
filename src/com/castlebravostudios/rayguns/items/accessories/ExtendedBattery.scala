package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.items.ItemAccessory
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack
import net.minecraft.block.Block

object ExtendedBattery extends Item( Config.extendedBattery ) with ItemAccessory {

  val moduleKey = "ExtendedBattery"
  val powerModifier = 2.0 / 3.0
  register
  setUnlocalizedName("rayguns.ExtendedBattery")
  setTextureName("rayguns:extended_battery")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "SI ",
    "IRI",
    " I ",
    'S' : Character, Block.stone,
    'I' : Character, Item.ingotIron,
    'R' : Character, Block.blockRedstone )
}