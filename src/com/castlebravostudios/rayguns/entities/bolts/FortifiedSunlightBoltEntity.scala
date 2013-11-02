package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemDye
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.entities.bolts.BaseBoltEntity


class FortifiedSunlightBoltEntity( world : World ) extends BaseBoltEntity(world) {

  override def colorRed : Float = 1.0f
  override def colorBlue : Float = 0.0f
  override def colorGreen : Float = 1.0f

  def hitEntity( entity : Entity ) : Unit = entity match {
    case e : EntityLivingBase if e.isEntityUndead() => hitUndead( e )
    case _ => hitLiving( entity )
  }

  private def hitUndead( entity : EntityLivingBase ) : Unit = {
    entity.setFire(8)
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), 8)
    spawnParticles()
  }

  private def hitLiving( entity : Entity ) : Unit = {
    entity.attackEntityFrom(new EntityDamageSource("fortifiedsunlight", shooter), 6)
    spawnParticles()
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
    spawnParticles()
  }

  private def spawnParticles( ) : Unit = {
    def randVel = rand.nextGaussian() * 0.02D
    def randPos( x : Double ) = x + ( rand.nextFloat().doubleValue() - 0.5)
    for ( _ <- 0 until 4 ) {
      world.spawnParticle("happyVillager",
        randPos( posX ), randPos( posY ), randPos( posZ ), randVel, randVel, randVel);
    }
  }
}