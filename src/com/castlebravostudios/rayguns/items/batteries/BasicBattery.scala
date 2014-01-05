package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.items.emitters.Emitters
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.ModularRayguns

object BasicBattery extends BaseRaygunModule with RaygunBattery {

  val moduleKey = "BasicBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.BasicBattery.segment"
  val maxCapacity = 1000

  def createItem( id : Int ) = new ItemModule( id, this )
    .setMaxDamage( maxCapacity )
    .setUnlocalizedName("rayguns.BasicBattery")
    .setTextureName("rayguns:battery_basic")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerRecipe() : Unit =
    GameRegistry.addRecipe( new ItemStack( item, 1 ),
      "SG ",
      "IRI",
      "IRI",
      'S' : Character, Emitters.shrinkRayEmitter,
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron,
      'R' : Character, Block.blockRedstone )
}