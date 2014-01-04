package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable

import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object LaserEffect extends BaseEffect {

  val effectKey = "Laser"

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shootable.shooter), 2f * shootable.charge.toFloat )
      true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/laser_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/laser_beam.png" )
}
