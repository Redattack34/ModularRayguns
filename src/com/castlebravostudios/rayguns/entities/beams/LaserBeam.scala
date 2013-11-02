package com.castlebravostudios.rayguns.entities.beams

import net.minecraft.client.particle.EntityFX
import net.minecraft.world.World
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EnumMovingObjectType

class LaserBeam(world : World) extends EntityFX( world, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d ) {

  var shooter : EntityLivingBase = _
  particleMaxAge = 3

  def onImpact( pos : MovingObjectPosition ) {
    createSmoke( pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord )

    pos.typeOfHit match {
      case EnumMovingObjectType.ENTITY => hitEntity( pos.entityHit )
      case EnumMovingObjectType.TILE => hitBlock( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
    }
  }

  override def onUpdate() : Unit = {
    super.onUpdate
  }

  def hitEntity( entity : Entity ) : Unit = {
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shooter), 2f)
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
  }

  private def createSmoke( x : Double, y : Double, z : Double ) {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", x, y, z, 0.0D, 0.0D, 0.0D);
    }
  }
}