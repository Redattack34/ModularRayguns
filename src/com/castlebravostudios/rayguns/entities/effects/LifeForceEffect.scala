package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import net.minecraft.util.ResourceLocation


trait LifeForceEffect extends BaseEffect {
  self : Shootable =>

  def hitEntity( hit : Entity ) : Boolean = {
    if ( hit.isInstanceOf[EntityLivingBase] ) {
      val living = hit.asInstanceOf[EntityLivingBase]

      if ( living.isEntityUndead() ) {
        living.attackEntityFrom(new EntityDamageSource("lifeforce", shooter), 4)
      }
      else {
        living.heal(4)
      }
    }

    true
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("cloud", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
  }
}

class LifeForceBoltEntity( world : World ) extends BaseBoltEntity( world ) with LifeForceEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/life_bolt.png" )
}
class LifeForceBeamEntity( world : World ) extends BaseBeamEntity( world ) with LifeForceEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/life_beam.png" )
}