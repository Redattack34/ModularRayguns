/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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

  //scalastyle:off parameter.number
  override def onBlockActivated(world : World, x : Int, y : Int, z : Int, player : EntityPlayer,
    metadata : Int, par7 : Float, par8 : Float, par9 : Float ) : Boolean = {

    val entity = world.getBlockTileEntity( x, y, z );
    if ( entity == null || player.isSneaking() ) {
      return false
    }

    openGui(player, world, x, y, z);
    true
  }
  //scalastyle:off

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