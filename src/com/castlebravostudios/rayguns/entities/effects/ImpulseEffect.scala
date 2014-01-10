package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.Vector3

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntityPiston
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ImpulseEffect extends BaseEffect {

  val effectKey = "Impulse"

  private def impulseStrength( shootable : Shootable ) = 1.5 * shootable.charge

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("impulse", shootable.shooter), 4f)

    val impulse = impulseVector(shootable).mult(impulseStrength(shootable))
    entity.addVelocity(impulse.x, impulse.y, impulse.z)
    true
  }

  def hitBlock(shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val hitPos = BlockPos( hitX, hitY, hitZ )
    val offset = hitOffset( invertSide( side ) )
    if ( canPushBlocks( shootable.worldObj, hitPos, offset, Math.round( shootable.charge.toFloat * 3 ) ) ) {
      doPushBlocks( shootable.worldObj, hitPos, offset, invertSide( side ) )
    }

    true
  }

  private def canPushBlocks( worldObj : World, block : BlockPos, offset : BlockPos, steps : Int ) : Boolean = {
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
    else canPushBlocks( worldObj, block.add( offset ), offset, steps - 1 )
  }

  private def doPushBlocks( worldObj : World, block : BlockPos, offset : BlockPos, side : Int ) : Unit = {
    val BlockPos( x, y, z ) = block
    val blockId = worldObj.getBlockId( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )
    val blockObj = Block.blocksList(blockId)

    if ( worldObj.isAirBlock(x, y, z) || blockObj.getMobilityFlag() == 1 ) return;
    doPushBlocks( worldObj, block.add( offset ), offset, side )

    worldObj.setBlockToAir(x, y, z)
    val BlockPos( x2, y2, z2 ) = block.add(offset)
    worldObj.setBlock(x2, y2, z2, Block.pistonMoving.blockID)
    worldObj.setBlockTileEntity(x2, y2, z2, new TileEntityPiston( blockId, meta, side, true, false ) )
  }

  protected def impulseVector( shootable : Shootable ) : Vector3 = {
    val x = -MathHelper.sin( shootable.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5;
    val y = 0.10D
    val z = MathHelper.cos( shootable.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5
    return Vector3( x, y, z )
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/impulse_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/impulse_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/impulse_charge.png" )
}
