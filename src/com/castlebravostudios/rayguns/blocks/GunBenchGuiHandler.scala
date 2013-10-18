package com.castlebravostudios.rayguns.blocks

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class GunBenchGuiHandler extends IGuiHandler {

  override def getServerGuiElement( id : Int, player : EntityPlayer, world: World, x : Int, y : Int, z : Int ) : Object = {
    world.getBlockTileEntity(x, y, z) match {
      case gunBench : GunBenchTileEntity => new GunBenchContainer( player.inventory, gunBench )
      case _ => null
    }
  }

  override def getClientGuiElement( id : Int, player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Object = {
    world.getBlockTileEntity(x, y, z) match {
      case gunBench : GunBenchTileEntity => new GunBenchGui( player.inventory, gunBench )
      case _ => null
    }
  }
}