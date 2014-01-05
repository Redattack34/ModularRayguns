package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ExtendedBattery extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "ExtendedBattery"
  val powerModifier = 2.0 / 3.0
  val nameSegmentKey = "rayguns.ExtendedBattery.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ExtendedBattery")
    .setTextureName("rayguns:extended_battery")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerRecipe() : Unit =
    GameRegistry.addRecipe( new ItemStack( item, 1 ),
      "SI ",
      "IRI",
      " I ",
      'S' : Character, Emitters.shrinkRayEmitter,
      'I' : Character, Item.ingotIron,
      'R' : Character, Block.blockRedstone )
}