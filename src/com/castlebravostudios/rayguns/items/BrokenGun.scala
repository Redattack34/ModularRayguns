package com.castlebravostudios.rayguns.items

import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs

class BrokenGun( id : Int ) extends Item( id ) {

  setMaxStackSize(1)
  setCreativeTab(CreativeTabs.tabCombat)
  setUnlocalizedName("rayguns.brokenRaygun")
}