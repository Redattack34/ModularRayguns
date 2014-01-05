package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ChargeLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "ChargeLens"
  val powerModifier = 1.5
  val nameSegmentKey = "rayguns.ChargeLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ChargeLens")
    .setTextureName("rayguns:lens_charge")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerRecipe() : Unit =
    GameRegistry.addShapelessRecipe( new ItemStack( item, 1 ),
      PreciseLens.item, BasicBattery.item )
}