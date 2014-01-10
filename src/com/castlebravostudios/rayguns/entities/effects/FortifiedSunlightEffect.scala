package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemDye
import net.minecraft.item.ItemStack
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation


object FortifiedSunlightEffect extends BaseEffect {

  val effectKey = "FortifiedSunlight"

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = entity match {
    case e : EntityLivingBase if e.isEntityUndead() => hitUndead( shootable, e ); true
    case _ => hitLiving( shootable, entity ); true
  }

  private def hitUndead( shootable : Shootable, entity : EntityLivingBase ) : Unit = {
    entity.setFire( Math.round( shootable.charge.toFloat * 3  ) )
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shootable.shooter), shootable.charge.toFloat * 4 )
  }

  private def hitLiving( shootable : Shootable, entity : Entity ) : Unit = {
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shootable.shooter), shootable.charge.toFloat * 3 )
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    if ( shootable.charge > 2.5 && shootable.shooter.isInstanceOf[EntityPlayer] ) {
      ItemDye.applyBonemeal(new ItemStack( Item.dyePowder, 1 ), shootable.worldObj,
          hitX, hitY, hitZ, shootable.shooter.asInstanceOf[EntityPlayer])
    }
    true
  }

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    def randVel = shootable.random.nextGaussian() * 0.02D
    def randPos( x : Double ) = x + ( shootable.random.nextFloat().doubleValue() - 0.5)
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("happyVillager",
        randPos( hitX ), randPos( hitY ), randPos( hitZ ), randVel, randVel, randVel);
    }
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/sunlight_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/sunlight_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/sunlight_charge.png" )
}
