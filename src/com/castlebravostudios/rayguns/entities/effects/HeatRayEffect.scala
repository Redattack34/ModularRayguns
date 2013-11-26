package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions


trait HeatRayEffect extends BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 0.0f
  def colourGreen : Float = 0.5f

  def hitEntity( hit : Entity ) : Boolean = {
    hit.setFire(4)
    hit.attackEntityFrom(new EntityDamageSource("heatray", shooter), 2)

    true
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    if ( worldObj.getBlockId(hitX, hitY, hitZ) == Block.ice.blockID ) {
      worldObj.setBlock( hitX, hitY, hitZ, Block.waterStill.blockID )
    }

    val (x, y, z) = adjustCoords( hitX, hitY, hitZ, side )
    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Block.fire.blockID)
      }
    }

    true
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()
}

class HeatRayBoltEntity( world : World ) extends BaseBoltEntity(world) with HeatRayEffect with NoDuplicateCollisions
class HeatRayBeamEntity( world : World ) extends BaseBeamEntity(world) with HeatRayEffect