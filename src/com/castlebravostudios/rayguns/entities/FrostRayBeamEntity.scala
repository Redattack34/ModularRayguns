package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import net.minecraft.potion.Potion
import net.minecraft.entity.EntityLivingBase


class FrostRayBeamEntity( world : World ) extends BaseBeamEntity(world) {

  override def colorRed : Float = 0.5f
  override def colorBlue : Float = 1.0f
  override def colorGreen : Float = 1.0f

  def hitEntity( hit : Entity ) : Unit = {
    createParticles()
    hit.attackEntityFrom(new EntityDamageSource("frostray", shooter), 2)

    hit match {
      case living : EntityLivingBase => {
        Potion.moveSlowdown.affectEntity(shooter, living, 0, 1.0d)
      }
    }

  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
    createParticles()

    val hit = Block.blocksList( world.getBlockId( hitX, hitY, hitZ ) )

    val block =
      if ( hit == Block.waterMoving || hit == Block.waterStill ) Block.ice
      else if ( hit == Block.lavaMoving ) Block.cobblestone
      else if ( hit == Block.lavaStill ) Block.obsidian
      else null

    if ( block != null ) {
      world.setBlock(hitX, hitY, hitZ, block.blockID)
    }
    else if ( hit.blockMaterial.blocksMovement && world.isAirBlock(hitX, hitY+1, hitZ) ) {
      world.setBlock( hitX, hitY+1, hitZ, Block.snow.blockID )
    }
  }

  override def collidesWithLiquids = true

  private def createParticles() {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
  }
}