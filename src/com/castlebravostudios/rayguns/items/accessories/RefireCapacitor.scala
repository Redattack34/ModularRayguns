package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object RefireCapacitor extends Item( Config.refireCapacitor ) with RaygunAccessory {
  val moduleKey = "RefireCapacitor"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.RefireCapacitor.segment"

  setUnlocalizedName("rayguns.RefireCapacitor")
  setTextureName("rayguns:refire_capacitor")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "SI ",
    "IRI",
    "G G",
    'S' : Character, Emitters.shrinkRayEmitter,
    'I' : Character, Item.ingotIron,
    'R' : Character, Block.blockRedstone,
    'G' : Character, Item.ingotGold )
}