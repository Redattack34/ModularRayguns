package com.castlebravostudios.rayguns.blocks.tech.invred

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.IBlockAccess
import java.util.Random
import net.minecraft.util.Vec3
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.block.ITileEntityProvider
import net.minecraft.tileentity.TileEntity

/**
 * Invisible block that emits a strong redstone signal to one side, depending
 * on the metadata. This is used for the lightning beam's redstone effect.
 */
class InvisibleRedstone(id : Int) extends Block( id, Material.air ) with ITileEntityProvider {

  setResistance(0)
  setBlockBounds(0, 0, 0, 0, 0, 0)
  disableStats()

  override def onBlockAdded( world : World, x : Int, y : Int, z : Int ) : Unit = {
    world.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y + 1, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
  }

  override def breakBlock(world : World, x : Int, y : Int, z : Int, blockId : Int, metadata : Int ) = {
    world.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y + 1, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
    world.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
  }

  override def isProvidingWeakPower( access : IBlockAccess, x : Int, y : Int, z : Int, side : Int ) : Int = {
    return 15
  }

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

  override def collisionRayTrace(world : World, x : Int, y : Int, z : Int, start : Vec3, end : Vec3) = null

  override def registerIcons( reg : IconRegister ) : Unit = ()

  override def isBlockReplaceable(world : World, x : Int, y : Int, z : Int) : Boolean = true

  override def createNewTileEntity( world : World ) : TileEntity = {
    val te = new InvisibleRedstoneTileEntity()
    te.setWorldObj(world)
    te
  }
}