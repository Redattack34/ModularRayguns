package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import java.util.Random
import com.castlebravostudios.rayguns.entities.BaseBeamEntity


trait FortifiedSunlightEffect extends BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 0.0f
  def colourGreen : Float = 1.0f

  def hitEntity( entity : Entity ) : Boolean = entity match {
    case e : EntityLivingBase if e.isEntityUndead() => hitUndead( e ); true
    case _ => hitLiving( entity ); true
  }

  private def hitUndead( entity : EntityLivingBase ) : Unit = {
    entity.setFire(8)
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), 8)
  }

  private def hitLiving( entity : Entity ) : Unit = {
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), 6)
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    def randVel = random.nextGaussian() * 0.02D
    def randPos( x : Double ) = x + ( random.nextFloat().doubleValue() - 0.5)
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("happyVillager",
        randPos( posX ), randPos( posY ), randPos( posZ ), randVel, randVel, randVel);
    }
  }
}

class FortifiedSunlightBoltEntity( world : World ) extends BaseBoltEntity(world) with FortifiedSunlightEffect
class FortifiedSunlightBeamEntity( world : World ) extends BaseBeamEntity(world) with FortifiedSunlightEffect