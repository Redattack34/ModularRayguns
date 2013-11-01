package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.entity.Entity


class LaserBoltEntity( world : World ) extends BaseBoltEntity(world) {

  override def colorRed : Float = 1.0f
  override def colorBlue : Float = 0.0f
  override def colorGreen : Float = 0.0f

  def hitEntity( entity : Entity ) : Unit = {
    createSmoke()
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shooter), 2f)
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
    createSmoke()
  }

  private def createSmoke() {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
  }
}