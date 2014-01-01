package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World


trait HeatRayEffect extends BaseEffect {
  self : Shootable =>

  def hitEntity( hit : Entity ) : Boolean = {
    hit.setFire( Math.round( charge.toFloat * 2 ) )
    hit.attackEntityFrom(new EntityDamageSource("heatray", shooter), charge.toFloat.round )

    true
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ): Boolean = {
    val BlockPos(centerX, centerY, centerZ) = adjustCoords( hitX, hitY, hitZ, side )
    val burnRadius = charge.toFloat.round

    for {
      x <- -burnRadius to burnRadius
      y <- -burnRadius to burnRadius
      z <- -burnRadius to burnRadius
      if ( x.abs + y.abs + z.abs < burnRadius )
    } {
      heatBlock(centerX + x, centerY + y, centerZ + z, side )
    }

    true
  }

  private def heatBlock(x: Int, y: Int, z: Int, side : Int): AnyVal = {
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

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()
}

class HeatRayBoltEntity( world : World ) extends BaseBoltEntity(world) with HeatRayEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/heat_bolt.png" )
}
class HeatRayBeamEntity( world : World ) extends BaseBeamEntity(world) with HeatRayEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/heat_beam.png" )
}