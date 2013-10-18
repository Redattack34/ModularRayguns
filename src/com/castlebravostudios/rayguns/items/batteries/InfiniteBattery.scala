package com.castlebravostudios.rayguns.items.batteries

import com.castlebravostudios.rayguns.api.defaults.DefaultItemBattery
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.src.ModLoader
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.Items
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.utils.GunComponents

class InfiniteBattery(id : Int) extends DefaultItemBattery( id ) {

  val moduleKey = defaultKey
  val powerModifier = 1.0d;
  setMaxDamage( Integer.MAX_VALUE )
  register
  setUnlocalizedName("rayguns.InfiniteBattery")
  setTextureName("rayguns:battery_infinite")

  override def drainPower( player : EntityPlayer, item : ItemStack, comp : GunComponents ) : Boolean = true
}