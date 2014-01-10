package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World


object LifeForceEffect extends BaseEffect {

  val effectKey = "LifeForce"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    if ( hit.isInstanceOf[EntityLivingBase] ) {
      val living = hit.asInstanceOf[EntityLivingBase]

      if ( living.isEntityUndead() ) {
        living.attackEntityFrom(new EntityDamageSource("lifeforce", shootable.shooter), shootable.charge.toFloat * 3 )
      }
      else {
        living.heal( shootable.charge.toFloat * 3 )
      }
    }

    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("cloud", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }
  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/life_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/life_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/life_charge.png" )
}
