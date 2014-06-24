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

package com.castlebravostudios.rayguns.blocks.tech.invred

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.IBlockAccess
import java.util.Random
import net.minecraft.util.Vec3
import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.MovingObjectPosition
import net.minecraft.client.renderer.texture.IIconRegister

/**
 * Invisible block that emits a strong redstone signal to one side, depending
 * on the metadata. This is used for the lightning beam's redstone effect.
 */
class InvisibleRedstone extends Block( Material.air ) with ITileEntityProvider {

  setResistance(0)
  setBlockBounds(0, 0, 0, 0, 0, 0)
  disableStats()

  override def onBlockAdded( world : World, x : Int, y : Int, z : Int ) : Unit = {
    world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
    world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
    world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
    world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
    world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
    world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
  }

  override def breakBlock(world : World, x : Int, y : Int, z : Int, blockId : Int, metadata : Int ) : Unit = {
    world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
    world.notifyBlocksOfNeighborChange(x, y + 1, z, this);
    world.notifyBlocksOfNeighborChange(x - 1, y, z, this);
    world.notifyBlocksOfNeighborChange(x + 1, y, z, this);
    world.notifyBlocksOfNeighborChange(x, y, z - 1, this);
    world.notifyBlocksOfNeighborChange(x, y, z + 1, this);
  }

  override def isProvidingWeakPower( access : IBlockAccess, x : Int, y : Int, z : Int, side : Int ) : Int = 15

  override def isProvidingStrongPower( access : IBlockAccess, x : Int, y : Int, z : Int, side : Int ) : Int = {
    val metadata = access.getBlockMetadata(x, y, z);
    if ( metadata == side ) 15 else 0
  }

  override def shouldSideBeRendered(access : IBlockAccess, x : Int, y : Int, z : Int, side : Int ) : Boolean = false

  override def isOpaqueCube() : Boolean = false

  override def isCollidable() : Boolean = false

  override def renderAsNormalBlock() : Boolean = false

  override def isAirBlock( world : World, x : Int, y : Int, z : Int ) : Boolean = true

  override def idPicked( world : World, x: Int, y : Int, z : Int) : Int = 0

  override def quantityDropped( rand : Random ) : Int = 0

  override def collisionRayTrace(world : World, x : Int, y : Int, z : Int,
      start : Vec3, end : Vec3) : MovingObjectPosition = null

  override def registerBlockIcons( reg : IIconRegister ) : Unit = ()

  override def isBlockReplaceable(world : World, x : Int, y : Int, z : Int) : Boolean = true

  override def createNewTileEntity( world : World ) : TileEntity = {
    val te = new InvisibleRedstoneTileEntity()
    te.setWorldObj(world)
    te
  }
}