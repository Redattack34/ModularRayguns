package com.castlebravostudios.rayguns.items.misc

import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils.getChargeDepleted

object BrokenGun extends Item( Config.brokenGun ) {

  import RaygunNbtUtils._

  setMaxStackSize(1)
  setCreativeTab(CreativeTabs.tabCombat)
  setUnlocalizedName("rayguns.brokenRaygun")

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0
  override def getMaxDamage( item: ItemStack ) : Int = RaygunNbtUtils.getMaxDamage( item )
}