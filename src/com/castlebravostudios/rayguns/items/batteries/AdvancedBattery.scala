package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.items.emitters.ShrinkRayEmitter

object AdvancedBattery extends Item( Config.advancedBattery ) with ItemBattery {

  val moduleKey = "AdvancedBattery"
  val powerModifier = 1.0d;
  setMaxDamage( 3000 )
  register
  setUnlocalizedName("rayguns.AdvancedBattery")
  setTextureName("rayguns:battery_advanced")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IBI",
      "IBI",
      'S' : Character, ShrinkRayEmitter,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'B' : Character, BasicBattery )
}