package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.utils.FireEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector

object InfiniteBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "InfiniteBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.InfiniteBattery.segment"
  val maxCapacity = Integer.MAX_VALUE

  def createItem( id : Int ) = new ItemBattery( id, this )
    .setMaxDamage( Integer.MAX_VALUE )
    .setUnlocalizedName("rayguns.InfiniteBattery")
    .setTextureName("rayguns:battery_infinite")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  override def drainPower( player : EntityPlayer, item : ItemStack, event : FireEvent ) : Boolean = true
}