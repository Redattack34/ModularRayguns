package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object BasicBattery extends Item( Config.basicBattery ) with ItemBattery {

  val moduleKey = "BasicBattery"
  val powerModifier = 1.0d;
  
  setMaxDamage( 1000 )
  setUnlocalizedName("rayguns.BasicBattery")
  setTextureName("rayguns:battery_basic")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IRI",
      "IRI",
      'S' : Character, Emitters.shrinkRayEmitter,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'R' : Character, Block.blockRedstone )
}