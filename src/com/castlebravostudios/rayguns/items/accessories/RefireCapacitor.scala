package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.items.ItemAccessory
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack
import net.minecraft.block.Block
import com.castlebravostudios.rayguns.items.emitters.ShrinkRayEmitter

object RefireCapacitor extends Item( Config.refireCapacitor ) with ItemAccessory {

  val moduleKey = "RefireCapacitor"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.RefireCapacitor")
  setTextureName("rayguns:refire_capacitor")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "SI ",
    "IRI",
    "G G",
    'S' : Character, ShrinkRayEmitter,
    'I' : Character, Item.ingotIron,
    'R' : Character, Block.blockRedstone,
    'G' : Character, Item.ingotGold )
}