package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object AdvancedBattery extends Item( Config.advancedBattery ) with RaygunBattery {

  val moduleKey = "AdvancedBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.AdvancedBattery.segment"

  setMaxDamage( 3000 )
  setUnlocalizedName("rayguns.AdvancedBattery")
  setTextureName("rayguns:battery_advanced")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IBI",
      "IBI",
      'S' : Character, Emitters.shrinkRayEmitter,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'B' : Character, BasicBattery )
}