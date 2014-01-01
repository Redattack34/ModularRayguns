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

trait ImpulseEffect extends Entity with BaseEffect {
  self : Shootable =>

  private def impulseStrength = 1.5 * charge

  def hitEntity( entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("impulse", shooter), 4f)

    val impulse = impulseVector.mult(impulseStrength)
    entity.addVelocity(impulse.x, impulse.y, impulse.z)
    true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val hitPos = BlockPos( hitX, hitY, hitZ )
    val offset = hitOffset( invertSide( side ) )
    if ( canPushBlocks( hitPos, offset, Math.round( charge.toFloat * 3 ) ) ) {
      doPushBlocks( hitPos, offset, invertSide( side ) )
    }

    true
  }

  private def canPushBlocks( block : BlockPos, offset : BlockPos, steps : Int ) : Boolean = {
    val BlockPos( x, y, z ) = block
    val blockId = worldObj.getBlockId( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )
    val blockObj = Block.blocksList(blockId)

    if ( steps < 0 ) false
    else if ( worldObj.isAirBlock(x, y, z) || blockObj.getMobilityFlag() == 1 ) true
    else if ( blockId == Block.obsidian.blockID ) false
    else if ( blockObj.getBlockHardness(worldObj, x, y, z ) == -1.0f ) false
    else if ( blockObj.getMobilityFlag() == 2 ) false
    else if ( worldObj.blockHasTileEntity( x, y, z ) ) false
    else canPushBlocks( block.add( offset ), offset, steps - 1 )
  }

  private def doPushBlocks( block : BlockPos, offset : BlockPos, side : Int ) : Unit = {
    val BlockPos( x, y, z ) = block
    val blockId = worldObj.getBlockId( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )
    val blockObj = Block.blocksList(blockId)

    if ( worldObj.isAirBlock(x, y, z) || blockObj.getMobilityFlag() == 1 ) return;
    doPushBlocks( block.add( offset ), offset, side )

    worldObj.setBlockToAir(x, y, z)
    val BlockPos( x2, y2, z2 ) = block.add(offset)
    worldObj.setBlock(x2, y2, z2, Block.pistonMoving.blockID)
    worldObj.setBlockTileEntity(x2, y2, z2, new TileEntityPiston( blockId, meta, side, true, false ) )
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()

  protected def impulseVector : Vector3 = {
    val x = -MathHelper.sin( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5;
    val y = 0.10D
    val z = MathHelper.cos( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5
    return Vector3( x, y, z )
  }

}

class ImpulseBoltEntity(world : World) extends BaseBoltEntity(world) with ImpulseEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/impulse_bolt.png" )
}
class ImpulseBeamEntity(world : World) extends BaseBeamEntity(world) with ImpulseEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/impulse_beam.png" )
}