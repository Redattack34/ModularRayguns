package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.TriggerOnDeath
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

trait ExplosiveEffect extends Entity with BaseEffect with TriggerOnDeath {
  self : Shootable =>

  def colourRed : Float = 0.5f
  def colourBlue : Float = 0.5f
  def colourGreen : Float = 0.5f

  def hitEntity( entity : Entity ) : Unit = ()
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = ()
  def createImpactParticles(hitX : Double, hitY : Double, hitZ: Double) = ()

  def triggerAt( x : Double, y : Double, z : Double ) = if ( worldObj.isOnServer ) {
    worldObj.newExplosion(shooter, x, y, z, 4, false, true)
  }
}

class ExplosiveBoltEntity(world : World) extends BaseBoltEntity(world) with ExplosiveEffect
class ExplosiveBeamEntity(world : World) extends BaseBeamEntity(world) with ExplosiveEffect