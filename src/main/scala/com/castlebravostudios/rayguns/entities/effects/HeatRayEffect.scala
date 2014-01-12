package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation


object HeatRayEffect extends BaseEffect {

  val effectKey = "HeatRay"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    hit.setFire( Math.round( shootable.charge.toFloat * 2 ) )
    hit.attackEntityFrom(new EntityDamageSource("heatray", shootable.shooter), shootable.charge.toFloat.round )

    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ): Boolean = {
    val BlockPos(centerX, centerY, centerZ) = adjustCoords( hitX, hitY, hitZ, side )
    val burnRadius = shootable.charge.toFloat.round

    for {
      x <- -burnRadius to burnRadius
      y <- -burnRadius to burnRadius
      z <- -burnRadius to burnRadius
      if ( x.abs + y.abs + z.abs < burnRadius )
    } {
      heatBlock( shootable, centerX + x, centerY + y, centerZ + z, side )
    }

    true
  }

  private def heatBlock( shootable : Shootable, x: Int, y: Int, z: Int, side : Int): AnyVal = {
    val worldObj = shootable.worldObj
    val shooter = shootable.shooter
    if ( worldObj.getBlockId(x, y, z) == Block.ice.blockID ) {
      worldObj.setBlock( x, y, z, Block.waterStill.blockID )
    }
    if ( shooter.isInstanceOf[EntityPlayer] &&
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Block.fire.blockID)
      }
    }
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/heat_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/heat_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/heat_charge.png" )
}
