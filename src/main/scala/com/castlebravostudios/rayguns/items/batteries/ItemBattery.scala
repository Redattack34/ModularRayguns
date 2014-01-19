package com.castlebravostudios.rayguns.items.batteries

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.MoreInformation
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.ItemModule

class ItemBattery( id : Int, battery : RaygunBattery ) extends ItemModule( id, battery ) with MoreInformation {

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] = {
    val maxCharge = RaygunNbtUtils.getMaxCharge( item )
    val depleted = RaygunNbtUtils.getChargeDepleted( item )
    List( (maxCharge - depleted) + "/" + maxCharge )
  }
}