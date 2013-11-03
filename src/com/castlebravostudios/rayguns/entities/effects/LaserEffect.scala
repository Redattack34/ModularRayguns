package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity

trait LaserEffect extends Entity with BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 0.0f
  def colourGreen : Float = 0.0f

  def hitEntity( entity : Entity ) : Unit = {
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shooter), 2f)
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = ()

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }
}

class LaserBoltEntity(world : World) extends BaseBoltEntity(world) with LaserEffect
class LaserBeamEntity(world : World) extends BaseBeamEntity(world) with LaserEffect