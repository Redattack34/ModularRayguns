package com.castlebravostudios.rayguns.blocks.gunbench

import java.util.Random
import com.castlebravostudios.rayguns.blocks._
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.blocks.BaseContainerBlock

class GunBench(id : Int) extends BaseContainerBlock(id) {
  setHardness(2.0F)
  setResistance(5.0f)
  setUnlocalizedName("rayguns.GunBench")
  setCreativeTab(CreativeTabs.tabBlock)

  def openGui( player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Unit =
    player.openGui(ModularRayguns, 0, world, x, y, z)

  override def createNewTileEntity( world : World ) : TileEntity = new GunBenchTileEntity()
}