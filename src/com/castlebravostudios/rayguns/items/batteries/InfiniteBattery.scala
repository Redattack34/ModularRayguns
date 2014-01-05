package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.FireEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object InfiniteBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "InfiniteBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.InfiniteBattery.segment"
  val maxCapacity = Integer.MAX_VALUE

  def createItem( id : Int ) = new ItemModule( id, this )
    .setMaxDamage( Integer.MAX_VALUE )
    .setUnlocalizedName("rayguns.InfiniteBattery")
    .setTextureName("rayguns:battery_infinite")

  override def drainPower( player : EntityPlayer, item : ItemStack, event : FireEvent ) : Boolean = true

  def registerRecipe() : Unit = ()

  ModuleRegistry.registerModule(this)
}