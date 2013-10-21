package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBattery
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.src.ModLoader
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.mod.Config

object UltimateBattery extends DefaultItemBattery( Config.item( "ultimateBattery" ) ) {

  val moduleKey = "UltimateBattery"
  val powerModifier = 1.0d;
  setMaxDamage( 5000 )
  register
  setUnlocalizedName("rayguns.UltimateBattery")
  setTextureName("rayguns:battery_ultimate")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IBI",
      "IBI",
      'S' : Character, Block.stone,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'B' : Character, AdvancedBattery )
}