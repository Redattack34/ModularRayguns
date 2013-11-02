package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import com.castlebravostudios.rayguns.entities.bolts.BaseBoltEntity


class HeatRayBoltEntity( world : World ) extends BaseBoltEntity(world) {

  override def colorRed : Float = 1.0f
  override def colorBlue : Float = 0.0f
  override def colorGreen : Float = 0.5f

  def hitEntity( hit : Entity ) : Unit = {
    hit.setFire(8)
    hit.attackEntityFrom(new EntityDamageSource("heatray", shooter), 2)
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
    if ( world.getBlockId(hitX, hitY, hitZ) == Block.ice.blockID ) {
      world.setBlock( hitX, hitY, hitZ, Block.waterStill.blockID )
    }

    val (x, y, z) = adjustCoords( hitX, hitY, hitZ, side )
    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( world.isAirBlock(x, y, z) ) {
        world.setBlock(x, y, z, Block.fire.blockID)
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