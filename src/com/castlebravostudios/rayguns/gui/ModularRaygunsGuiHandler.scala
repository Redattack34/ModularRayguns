package com.castlebravostudios.rayguns.gui

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchContainer
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchGui
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderContainer
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui

class ModularRaygunsGuiHandler extends IGuiHandler {

  override def getServerGuiElement( id : Int, player : EntityPlayer, world: World, x : Int, y : Int, z : Int ) : Object = {
    world.getBlockTileEntity(x, y, z) match {
      case gunBench : GunBenchTileEntity => new GunBenchContainer( player.inventory, gunBench )
      case lensGrinder : LensGrinderTileEntity => new LensGrinderContainer( player.inventory, lensGrinder )
      case _ => null
    }
  }

  override def getClientGuiElement( id : Int, player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Object = {
    world.getBlockTileEntity(x, y, z) match {
      case gunBench : GunBenchTileEntity => new GunBenchGui( player.inventory, gunBench )
      case lensGrinder : LensGrinderTileEntity => new LensGrinderGui( player.inventory, lensGrinder )
      case _ => null
    }
  }
}