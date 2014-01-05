package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.ModuleRegistry

object UltimateBattery extends Item( Config.ultimateBattery ) with ItemBattery {
  val moduleKey = "UltimateBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.UltimateBattery.segment"

  setMaxDamage( 5000 )
  setUnlocalizedName("rayguns.UltimateBattery")
  setTextureName("rayguns:battery_ultimate")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IBI",
      "IBI",
      'S' : Character, Emitters.shrinkRayEmitter,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'B' : Character, AdvancedBattery )
}