package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.FiringConfiguration

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object InfiniteBattery extends Item( Config.infiniteBattery ) with ItemBattery {

  val moduleKey = "InfiniteBattery"
  val powerModifier = 1.0d;
  setMaxDamage( Integer.MAX_VALUE )
  register
  setUnlocalizedName("rayguns.InfiniteBattery")
  setTextureName("rayguns:battery_infinite")

  override def drainPower( player : EntityPlayer, item : ItemStack, comp : FiringConfiguration ) : Boolean = true
}