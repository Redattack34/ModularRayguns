package com.castlebravostudios.rayguns.utils

import net.minecraft.world.World
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object Extensions {
  implicit class WorldExtension(val world : World) extends AnyVal {
    def isOnClient = world.isRemote
    def isOnServer = !world.isRemote

    def debug(name : String, value : Any) = {
      if ( isOnClient ) println( "Client " + name + ": " + value )
      if ( isOnServer ) println( "Server " + name + ": " + value )
    }
  }

  implicit class ItemStackExtension( val item : ItemStack ) extends AnyVal {

    def getTagCompoundSafe : NBTTagCompound = {
      if ( item.getTagCompound() == null ) {
        item.setTagCompound( new NBTTagCompound() )
      }
      item.getTagCompound()
    }

  }
}