package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World


object FrostRayEffect extends BaseEffect {

  val effectKey = "FrostRay"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    hit.attackEntityFrom(new EntityDamageSource("frostray", shootable.shooter),
        shootable.charge.toFloat * 2 )

    val livingShooter = shootable.shooter match{
      case l : EntityLivingBase => l
      case _ => null
    }

    hit match {
      case living : EntityLivingBase => {
        Potion.moveSlowdown.affectEntity(livingShooter, living,
            shootable.charge.toFloat.round, 1.0d)
      }
    }

    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ): Boolean = {
    val BlockPos(centerX, centerY, centerZ) = adjustCoords( hitX, hitY, hitZ, side )
    val freezeRadius = shootable.charge.toFloat.round
    for {
      x <- -freezeRadius to freezeRadius
      y <- -freezeRadius to freezeRadius
      z <- -freezeRadius to freezeRadius
      if ( x.abs + y.abs + z.abs < freezeRadius )
    } {
      tryFreezeBlock( shootable.worldObj, centerX + x, centerY + y, centerZ + z )
    }

    true
  }

  private def tryFreezeBlock( worldObj : World, hitX: Int, hitY: Int, hitZ: Int  ): AnyVal = {
    val material = worldObj.getBlockMaterial(hitX, hitY, hitZ)
    val metadata = worldObj.getBlockMetadata(hitX, hitY, hitZ)
    val frozenBlock =
      if ( material == Material.water ) Block.ice
      else if ( material == Material.lava && metadata == 0 ) Block.obsidian
      else if ( material == Material.lava && metadata <= 4 ) Block.cobblestone
      else null

    if ( frozenBlock != null ) {
      worldObj.setBlock(hitX, hitY, hitZ, frozenBlock.blockID)
    }
    else if ( material.blocksMovement && worldObj.isAirBlock(hitX, hitY+1, hitZ) ) {
      worldObj.setBlock( hitX, hitY+1, hitZ, Block.snow.blockID )
    }
  }

  override def collidesWithLiquids(shootable : Shootable) = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("snowballpoof", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/frost_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/frost_beam.png" )
}
