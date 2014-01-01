package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import com.castlebravostudios.rayguns.utils.Vector3
import com.castlebravostudios.rayguns.utils.MidpointDisplacement
import scala.collection.SortedSet
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.Extensions._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import net.minecraft.entity.monster.EntityCreeper
import com.castlebravostudios.rayguns.utils.BlockPos
import net.minecraft.util.ResourceLocation

trait LightningEffect extends Entity with BaseEffect {
  self : Shootable =>

  var pointsList : Seq[Vector3] = Seq()

  //Number of times this beam/bolt has been rendered. Used for rendering.
  var renderCount : Int = 0

  def hitEntity( entity : Entity ) : Boolean = {
    if ( entity.isInstanceOf[EntityCreeper] ) return true

    entity.attackEntityFrom(
      new EntityDamageSource("lightningray", shooter), 4f)
      true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {

    val BlockPos(x, y, z) = adjustCoords( hitX, hitY, hitZ, side )
    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Config.invisibleRedstone, side, 3)
      }
    }
    true
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }
}

class LightningBoltEntity(world : World) extends BaseBoltEntity(world) with LightningEffect with NoDuplicateCollisions {

  override def onUpdate() : Unit = {
    super.onUpdate()

    if ( world.isOnClient ) {
      pointsList = MidpointDisplacement.getBoltList
    }
  }

  override val texture = null //We use the beam texture for lightning bolts.
}
class LightningBeamEntity(world : World) extends BaseBeamEntity(world) with LightningEffect {
  override def depletionRate = 0.2d

  override val texture = new ResourceLocation( "rayguns", "textures/beams/lightning_beam.png" )
}