package com.castlebravostudios.rayguns.api.items

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.entity.Entity

trait ItemAccessory extends ItemModule {

  def onGunUpdate( world : World, entity : Entity, stack : ItemStack ) : Unit = Unit
}