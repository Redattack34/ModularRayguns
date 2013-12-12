package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.blocks.BaseContainerBlock
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class LensGrinder( id : Int ) extends BaseContainerBlock( id ) {
  setHardness(2.0F)
  setResistance(5.0f)
  setUnlocalizedName("rayguns.LensGrinder")
  setCreativeTab(ModularRayguns.raygunsTab)

  def openGui( player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Unit =
    player.openGui(ModularRayguns, 1, world, x, y, z)

  override def createNewTileEntity( world : World ) : TileEntity = new LensGrinderTileEntity()
}