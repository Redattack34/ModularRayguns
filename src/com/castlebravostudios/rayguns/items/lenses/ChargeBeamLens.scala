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

object ChargeBeamLens extends BaseRaygunModule with RaygunLens {
  val moduleKey = "ChargeBeamLens"
  val powerModifier = 1.2
  val nameSegmentKey = "rayguns.ChargeBeamLens.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ChargeBeamLens")
    .setTextureName("rayguns:lens_charge_beam")

  ModuleRegistry.registerModule(this)
  def registerRecipe() : Unit =
    GameRegistry.addShapelessRecipe( new ItemStack( item, 1 ),
        PreciseBeamLens, BasicBattery )
}