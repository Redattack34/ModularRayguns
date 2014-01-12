package com.castlebravostudios.rayguns.utils

import net.minecraft.world.World

object Extensions {
  implicit class WorldExtension(val world : World) extends AnyVal {
    def isOnClient = world.isRemote
    def isOnServer = !world.isRemote

    def debug(name : String, value : Any) = {
      if ( isOnClient ) println( "Client " + name + ": " + value )
      if ( isOnServer ) println( "Server " + name + ": " + value )
    }
  }
}