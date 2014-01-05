package com.castlebravostudios.rayguns.api.items

import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs

class ItemModule( id : Int, val module : RaygunModule ) extends Item( id ) {

  //Override the setters to return ItemModule for easier chaining in the modules.
  override def setContainerItem( item : Item ) : ItemModule = { super.setContainerItem(item); this }
  override def setCreativeTab( tab : CreativeTabs ) : ItemModule = { super.setCreativeTab(tab); this }
  override def setFull3D() : ItemModule = { super.setFull3D(); this }
  override def setHasSubtypes( hasSubtypes : Boolean ) : ItemModule = { super.setHasSubtypes(hasSubtypes); this }
  override def setMaxDamage( maxDamage : Int ) : ItemModule = { super.setMaxDamage(maxDamage); this }
  override def setMaxStackSize( stackSize : Int ) : ItemModule = { super.setMaxStackSize(stackSize); this }
  override def setNoRepair() : ItemModule = { super.setNoRepair(); this }
  override def setPotionEffect( effect : String ) : ItemModule = { super.setPotionEffect(effect); this }
  override def setTextureName( texture : String ) : ItemModule = { super.setTextureName(texture); this }
  override def setUnlocalizedName( name : String ) : ItemModule = { super.setUnlocalizedName(name); this }
}