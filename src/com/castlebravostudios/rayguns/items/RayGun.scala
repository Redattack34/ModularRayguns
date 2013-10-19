package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemModule
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.castlebravostudios.rayguns.blocks.GunBenchTileEntity
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.GunComponents

class RayGun(id : Int) extends Item(id) {

  import RaygunNbtUtils._

  setMaxStackSize(1)
  setCreativeTab(CreativeTabs.tabCombat)
  setUnlocalizedName("rayguns.raygun")

  override def onItemRightClick(item : ItemStack, world : World, player : EntityPlayer ) : ItemStack = {
    val components = getComponents( item )
    val creator = components.flatMap( BeamRegistry.getFunction )
    creator match {
      case Some( f ) => { fire(item, components.get, world, player, f); item }
      case None => buildBrokenGun( item )
    }
  }


  private def fire( item : ItemStack, components : GunComponents,
      world : World, player : EntityPlayer, f : BeamRegistry.BeamCreator ): Unit = {
    if ( components.battery.drainPower( player, item, components ) ){
      f( world, player )
    }
  }

  override def getMaxDamage( item: ItemStack ) : Int =
    getComponents( item ).map( _.battery.maxCapacity ).getOrElse(Integer.MAX_VALUE)
}