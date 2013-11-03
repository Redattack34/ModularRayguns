package com.castlebravostudios.rayguns.utils

import net.minecraft.world.World

object Extensions {
  implicit class WorldExtension(val world : World) extends AnyVal {
    def isOnClient = world.isRemote
    def isOnServer = !world.isRemote
  }
}