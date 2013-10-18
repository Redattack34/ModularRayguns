package com.castlebravostudios.rayguns.api.defaults

import com.castlebravostudios.rayguns.api.items.ItemModule
import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.creativetab.CreativeTabSearch
import com.castlebravostudios.rayguns.api.ModuleRegistry

abstract class DefaultItemModule(id : Int) extends Item(id) with ItemModule {

  setMaxStackSize(1)
  setCreativeTab(CreativeTabs.tabCombat)

  protected def defaultKey = getClass.getSimpleName
  protected def register = ModuleRegistry.registerModule(this)
}