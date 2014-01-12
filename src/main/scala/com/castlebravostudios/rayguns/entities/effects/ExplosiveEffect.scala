package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.TriggerOnDeath
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityTNTPrimed
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ExplosiveEffect extends BaseEffect with TriggerOnDeath {

  val effectKey = "Explosive"

  /**
   * Fake 'EntityTNTPrimed' which is used to give the correct owner when causing
   * the explosion.
   */
  private class FakeTNTPrimed( shootable : Shootable ) extends EntityTNTPrimed( shootable.worldObj ) {
    override def getTntPlacedBy = shootable.shooter match {
      case living : EntityLivingBase => living
      case _ => null //Should never happen
    }
  }

  def hitEntity( entity : Entity ) = true
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int )  = true
  def createImpactParticles(hitX : Double, hitY : Double, hitZ: Double) = ()

  def triggerAt( shootable : Shootable, x : Double, y : Double, z : Double ) =
    if ( shootable.worldObj.isOnServer ) {
      shootable.worldObj.newExplosion(new FakeTNTPrimed( shootable ), x, y, z, 3.0f, false, true)
    }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/explosive_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/explosive_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/explosive_charge.png" )
}
