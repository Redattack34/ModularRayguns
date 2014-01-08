package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.mod.ModularRayguns

object UltimateBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "UltimateBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.UltimateBattery.segment"
  val maxCapacity = 5000

  def createItem( id : Int ) = new ItemModule( id, this )
    .setMaxDamage( maxCapacity )
    .setUnlocalizedName("rayguns.UltimateBattery")
    .setTextureName("rayguns:battery_ultimate")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}