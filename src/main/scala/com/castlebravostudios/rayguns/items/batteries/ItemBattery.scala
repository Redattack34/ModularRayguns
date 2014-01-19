package com.castlebravostudios.rayguns.items.batteries

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.MoreInformation
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector

class ItemBattery( id : Int, val battery : RaygunBattery ) extends ItemModule( id, battery ) with MoreInformation
  with RFItemPowerConnector with IC2ItemPowerConnector {

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] = {
    val maxCharge = RaygunNbtUtils.getMaxCharge( item )
    val depleted = RaygunNbtUtils.getChargeDepleted( item )
    List( (maxCharge - depleted) + "/" + maxCharge )
  }

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = RaygunNbtUtils.getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0

  override def getMaxDamage( item: ItemStack ) : Int = RaygunNbtUtils.getMaxCharge( item )
}