package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.items.MoreInformation
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

object BrokenGun extends ScalaItem( Config.brokenGun ) with MoreInformation {

  import RaygunNbtUtils._

  setMaxStackSize(1)
  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.BrokenRaygun")

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] =
    RaygunNbtUtils.getComponentInfo(item)

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int =
    getBattery(item).map( _.getChargeDepleted( item ) ).getOrElse( 0 )
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0
  override def getMaxDamage( item: ItemStack ) : Int =
    getBattery(item).map( _.maxCapacity ).getOrElse(1)
}