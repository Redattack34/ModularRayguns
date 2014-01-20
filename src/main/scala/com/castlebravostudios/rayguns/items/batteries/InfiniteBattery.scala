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
import net.minecraft.client.resources.I18n

object InfiniteBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "InfiniteBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.InfiniteBattery.segment"
  val maxCapacity = Integer.MAX_VALUE
  val maxChargePerTick = 16
  val ic2Tier = 4

  def createItem( id : Int ) = new ItemBattery( id, this )
    .setUnlocalizedName("rayguns.InfiniteBattery")
    .setTextureName("rayguns:battery_infinite")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  override def getChargeDepleted( item : ItemStack ) = 0
  override def setChargeDepleted( item : ItemStack, depleted : Int ) = ()
  override def addCharge( item : ItemStack, delta : Int ) : Unit = ()
  override def getChargeString( item : ItemStack ) = I18n.getString("rayguns.infinityCharge")
}