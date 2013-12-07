package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import net.minecraft.util.ResourceLocation


trait FrostRayEffect extends BaseEffect {
  self : Shootable =>

  def hitEntity( hit : Entity ) : Boolean = {
    hit.attackEntityFrom(new EntityDamageSource("frostray", shooter), 2)

    val livingShooter = shooter match{
      case l : EntityLivingBase => l
      case _ => null
    }

    hit match {
      case living : EntityLivingBase => {
        Potion.moveSlowdown.affectEntity(livingShooter, living, 0, 1.0d)
      }
    }

    true
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val material = worldObj.getBlockMaterial(hitX, hitY, hitZ)
    val metadata = worldObj.getBlockMetadata(hitX, hitY, hitZ)

    val block =
      if ( material == Material.water ) Block.ice
      else if ( material == Material.lava && metadata == 0 ) Block.obsidian
      else if ( material == Material.lava && metadata <= 4 ) Block.cobblestone
      else null

    if ( block != null ) {
      worldObj.setBlock(hitX, hitY, hitZ, block.blockID)
    }
    else if ( material.blocksMovement && worldObj.isAirBlock(hitX, hitY+1, hitZ) && side == 1 ) {
      worldObj.setBlock( hitX, hitY+1, hitZ, Block.snow.blockID )
    }

    true
  }

  override def collidesWithLiquids = true

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("snowballpoof", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }
}

class FrostRayBoltEntity( world : World ) extends BaseBoltEntity(world) with FrostRayEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/frost_bolt.png" )
}
class FrostRayBeamEntity( world : World ) extends BaseBeamEntity(world) with FrostRayEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/frost_beam.png" )
}