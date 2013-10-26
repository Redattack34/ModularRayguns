package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource


class LaserBeamEntity( world : World ) extends BaseBeamEntity(world) {

  override def onImpact( pos : MovingObjectPosition ) {

    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }

    if ( pos.entityHit != null ) {
      pos.entityHit.attackEntityFrom(
          new EntityDamageSource("laser", shooter),2f)
    }
    super.onImpact(pos)
  }
}