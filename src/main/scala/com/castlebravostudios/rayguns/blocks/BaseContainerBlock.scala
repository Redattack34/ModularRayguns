package com.castlebravostudios.rayguns.blocks

import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.BlockContainer
import net.minecraft.world.World
import java.util.Random
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.entity.item.EntityItem
import net.minecraft.block.material.Material
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack
import net.minecraft.block.material.MapColor

abstract class BaseContainerBlock(id : Int) extends BlockContainer(id, new Material(MapColor.ironColor)) {

  override def onBlockActivated(world : World, x : Int, y : Int, z : Int, player : EntityPlayer,
    metadata : Int, par7 : Float, par8 : Float, par9 : Float ) : Boolean = {

    val entity = world.getBlockTileEntity( x, y, z );
    if ( entity == null || player.isSneaking() ) {
      return false;
    }

    openGui(player, world, x, y, z);
    true
  }

  override def breakBlock( world : World, x : Int, y : Int, z : Int, par5 : Int, par6 : Int ) : Unit = {
    dropItems( world, x, y, z )
    super.breakBlock(world, x, y, z, par5, par6);
  }

  private def dropItems( world : World, x : Int, y : Int, z : Int ) : Unit = {
    val rand = new Random()

    val tileEntity = world.getBlockTileEntity(x, y, z)
    if ( !tileEntity.isInstanceOf[IInventory]) { return }
    val inv = tileEntity.asInstanceOf[IInventory]

    for ( i <- 0 until inv.getSizeInventory();
          item = inv.getStackInSlot(i)
          if ( item != null && item.stackSize > 0 ) ) {
      val rx = rand.nextFloat() * 0.8F + 0.1F;
      val ry = rand.nextFloat() * 0.8F + 0.1F;
      val rz = rand.nextFloat() * 0.8F + 0.1F;

      val entityItem = new EntityItem(world,
                      x + rx, y + ry, z + rz,
                      new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

      if (item.hasTagCompound()) {
              entityItem.getEntityItem().setTagCompound(item.getTagCompound().copy().asInstanceOf[NBTTagCompound]);
      }

      val factor = 0.05F;
      entityItem.motionX = rand.nextGaussian() * factor;
      entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
      entityItem.motionZ = rand.nextGaussian() * factor;
      world.spawnEntityInWorld(entityItem);
      item.stackSize = 0;
    }
  }

  def openGui( player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Unit
}