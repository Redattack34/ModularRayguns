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
import net.minecraft.block.material.Material
import com.castlebravostudios.rayguns.entities.bolts.BaseBoltEntity


class FrostRayBoltEntity( world : World ) extends BaseBoltEntity(world) {

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

    val material = world.getBlockMaterial(hitX, hitY, hitZ)
    val metadata = world.getBlockMetadata(hitX, hitY, hitZ)

    val block =
      if ( material == Material.water ) Block.ice
      else if ( material == Material.lava && metadata == 0 ) Block.obsidian
      else if ( material == Material.lava && metadata <= 4 ) Block.cobblestone
      else null

    if ( block != null ) {
      world.setBlock(hitX, hitY, hitZ, block.blockID)
    }
    else if ( material.blocksMovement && world.isAirBlock(hitX, hitY+1, hitZ) && side == 1 ) {
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