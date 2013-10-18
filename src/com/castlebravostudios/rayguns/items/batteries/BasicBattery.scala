package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBattery
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.src.ModLoader
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item

class BasicBattery(id : Int) extends DefaultItemBattery( id ) {

  val moduleKey = defaultKey
  val powerModifier = 1.0d;
  setMaxDamage( 10000 )
  register
  setUnlocalizedName("rayguns.BasicBattery")
  setTextureName("rayguns:battery_basic")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
      "SG ",
      "IRI",
      "IRI",
      'S' : Character, Block.stone,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'R' : Character, Block.blockRedstone )
}