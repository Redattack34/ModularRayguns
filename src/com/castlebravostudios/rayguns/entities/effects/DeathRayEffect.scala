package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import net.minecraftforge.common.IPlantable
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.items.misc.RayGun
import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.ResourceLocation

trait DeathRayEffect extends Entity with BaseEffect {
  self : Shootable =>

  def hitEntity( hit : Entity ) : Boolean = {
    if ( hit.isInstanceOf[EntityLivingBase] ) {
      val living = hit.asInstanceOf[EntityLivingBase]

      if ( living.isEntityUndead() ) {
        living.heal(4)
      }
      else {
        hit.attackEntityFrom(
          new EntityDamageSource("deathRay", shooter), 10f)
      }
    }

    false
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {

    val blockId = worldObj.getBlockId(hitX, hitY, hitZ)
    val block = Block.blocksList(blockId)
    if ( canEdit( hitX, hitY, hitZ, side ) && blockMatch.isDefinedAt( block ) ) {
      worldObj.setBlock(hitX, hitY, hitZ, blockMatch( block ))
    }

    false
  }

  private def canEdit( x : Int, y : Int, z : Int, side : Int ) : Boolean = {
    shooter match {
      case player : EntityPlayer => player.canPlayerEdit(x, y, z, side, new ItemStack( RayGun ) )
      case _ => false
    }
  }

  private val blockMatch : PartialFunction[Block, Int] = {
    case i : IPlantable => 0
    case b if b == Block.grass => Block.dirt.blockID
    case b if b == Block.mycelium => Block.dirt.blockID
    case b if b == Block.leaves => 0
    case b if b == Block.vine => 0
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()
}

class DeathRayBoltEntity(world : World) extends BaseBoltEntity(world) with DeathRayEffect with NoDuplicateCollisions {
  override val texture = new ResourceLocation( "rayguns", "textures/bolts/death_ray_bolt.png" )
}
class DeathRayBeamEntity(world : World) extends BaseBeamEntity(world) with DeathRayEffect {
  override val texture = new ResourceLocation( "rayguns", "textures/beams/death_ray_beam.png" )
}