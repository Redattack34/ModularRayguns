package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World


trait HeatRayEffect extends BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 0.0f
  def colourGreen : Float = 0.5f

  def hitEntity( hit : Entity ) : Unit = {
    hit.setFire(8)
    hit.attackEntityFrom(new EntityDamageSource("heatray", shooter), 2)
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
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
  }

  private def adjustCoords( x : Int, y : Int, z : Int, side : Int ) : (Int, Int, Int) = side match {
    case 0 => (x, y - 1, z)
    case 1 => (x, y + 1, z)
    case 2 => (x, y, z - 1)
    case 3 => (x, y, z + 1)
    case 4 => (x - 1, y, z)
    case 5 => (x + 1, y, z)
  }
}

class HeatRayBoltEntity( world : World ) extends BaseBoltEntity(world) with HeatRayEffect