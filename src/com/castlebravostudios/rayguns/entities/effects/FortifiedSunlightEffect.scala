package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import java.util.Random
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import net.minecraft.util.ResourceLocation
import net.minecraft.item.ItemDye
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer


trait FortifiedSunlightEffect extends BaseEffect {
  self : Shootable =>

  def hitEntity( entity : Entity ) : Boolean = entity match {
    case e : EntityLivingBase if e.isEntityUndead() => hitUndead( e ); true
    case _ => hitLiving( entity ); true
  }

  private def hitUndead( entity : EntityLivingBase ) : Unit = {
    entity.setFire( Math.round( charge.toFloat * 3  ) )
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), charge.toFloat * 4 )
  }

  private def hitLiving( entity : Entity ) : Unit = {
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), charge.toFloat * 3 )
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    if ( charge > 2.5 && shooter.isInstanceOf[EntityPlayer] ) {
      ItemDye.applyBonemeal(new ItemStack( Item.dyePowder, 1 ), worldObj,
          hitX, hitY, hitZ, shooter.asInstanceOf[EntityPlayer])
    }
    true
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    def randVel = random.nextGaussian() * 0.02D
    def randPos( x : Double ) = x + ( random.nextFloat().doubleValue() - 0.5)
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("happyVillager",
        randPos( hitX ), randPos( hitY ), randPos( hitZ ), randVel, randVel, randVel);
    }
  }
}

class FortifiedSunlightBoltEntity( world : World ) extends BaseBoltEntity(world) with FortifiedSunlightEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/sunlight_bolt.png" )
}
class FortifiedSunlightBeamEntity( world : World ) extends BaseBeamEntity(world) with FortifiedSunlightEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/sunlight_beam.png" )
}