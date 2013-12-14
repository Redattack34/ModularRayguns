package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.block.Block
import net.minecraft.block.BlockPistonBase
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntityPiston
import net.minecraft.util.ResourceLocation

trait TractorEffect extends Entity with BaseEffect {
  self : Shootable =>

  private def impulseStrength = 1.5

  def hitEntity( entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("impulse", shooter), 4f)

    val impulse = impulseVector.mult(impulseStrength)
    entity.addVelocity(impulse.x, impulse.y, impulse.z)
    true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val hitPos = BlockPos( hitX, hitY, hitZ )
    val offset = hitOffset( side )
    if ( canPullBlock( hitPos, offset ) ) {
      doPushBlocks( hitPos, offset, side )
    }

    true
  }

  private def canPullBlock( block : BlockPos, offset : BlockPos ) : Boolean = {
    val BlockPos( x, y, z ) = block
    val blockId = worldObj.getBlockId( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )
    val blockObj = Block.blocksList(blockId)

    if ( worldObj.isAirBlock(x, y, z) || blockObj.getMobilityFlag() == 1 ) true
    else if ( blockId == Block.obsidian.blockID ) false
    else if ( blockObj.getBlockHardness(worldObj, x, y, z ) == -1.0f ) false
    else if ( blockObj.getMobilityFlag() == 2 ) false
    else if ( worldObj.blockHasTileEntity( x, y, z ) ) false
    else true
  }

  private def doPushBlocks( block : BlockPos, offset : BlockPos, side : Int ) : Unit = {
    val BlockPos( x, y, z ) = block
    val blockId = worldObj.getBlockId( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )
    val blockObj = Block.blocksList(blockId)

    if ( worldObj.isAirBlock(x, y, z) || blockObj.getMobilityFlag() == 1 ) return;

    worldObj.setBlockToAir(x, y, z)
    val BlockPos( x2, y2, z2 ) = block.add(offset)
    worldObj.setBlock(x2, y2, z2, Block.pistonMoving.blockID)
    worldObj.setBlockTileEntity(x2, y2, z2, new TileEntityPiston( blockId, meta, side, true, false ) )
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()

  protected def impulseVector : Vector3 = {
    val x = MathHelper.sin( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5;
    val y = 0.10D
    val z = -MathHelper.cos( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5
    return Vector3( x, y, z )
  }

}

class TractorBoltEntity(world : World) extends BaseBoltEntity(world) with TractorEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/tractor_bolt.png" )
}
class TractorBeamEntity(world : World) extends BaseBeamEntity(world) with TractorEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/tractor_beam.png" )
}