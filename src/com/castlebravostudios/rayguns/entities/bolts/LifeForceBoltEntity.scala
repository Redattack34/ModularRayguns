package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import com.castlebravostudios.rayguns.entities.bolts.BaseBoltEntity


class LifeForceBoltEntity( world : World ) extends BaseBoltEntity(world) {

  override def colorRed : Float = 1.0f
  override def colorBlue : Float = 1.0f
  override def colorGreen : Float = 1.0f

  def hitEntity( hit : Entity ) : Unit = {
    createParticles()
    if ( hit.isInstanceOf[EntityLivingBase] ) {
      val living = hit.asInstanceOf[EntityLivingBase]

      if ( living.isEntityUndead() ) {
        living.attackEntityFrom(new EntityDamageSource("lifeforce", shooter), 4)
      }
      else {
        living.heal(4)
      }
    }
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
    createParticles()
  }

  private def createParticles() {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("cloud", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
  }
}