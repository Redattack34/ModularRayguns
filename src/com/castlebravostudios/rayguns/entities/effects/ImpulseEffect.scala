package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import net.minecraft.util.MathHelper
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.entity.EntityLiving

trait ImpulseEffect extends Entity with BaseEffect {
  self : Shootable =>

  private def impulseStrength = 1.5

  def colourRed : Float = 0.6f
  def colourBlue : Float = 0.6f
  def colourGreen : Float = 1.0f

  def hitEntity( entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("impulse", shooter), 4f)

    val impulse = impulseVector.mult(impulseStrength)
    entity.addVelocity(impulse.x, impulse.y, impulse.z)
    true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    true
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  protected def impulseVector : Vector3 = {
    val x = -MathHelper.sin( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5;
    val y = 0.10D
    val z = MathHelper.cos( this.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5
    return Vector3( x, y, z )
  }
}

class ImpulseBoltEntity(world : World) extends BaseBoltEntity(world) with ImpulseEffect with NoDuplicateCollisions
class ImpulseBeamEntity(world : World) extends BaseBeamEntity(world) with ImpulseEffect