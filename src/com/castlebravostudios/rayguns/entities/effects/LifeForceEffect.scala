package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World


trait LifeForceEffect extends BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 1.0f
  def colourGreen : Float = 1.0f

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

class LifeForceBoltEntity( world : World ) extends BaseBoltEntity( world ) with LifeForceEffect