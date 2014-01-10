package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
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

object LightningEffect extends BaseEffect {

  val effectKey = "Lightning"

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    if ( entity.isInstanceOf[EntityCreeper] ) return true

    entity.attackEntityFrom(
      new EntityDamageSource("lightningRay", shootable.shooter), shootable.charge.toFloat * 4.0f )
      true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val shooter = shootable.shooter
    val BlockPos(x, y, z) = adjustCoords( hitX, hitY, hitZ, side )
    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Config.invisibleRedstone, side, 3)
      }
    }
    true
  }

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  override def createBeamEntity( world : World ) : LightningBeamEntity = {
    val beam = new LightningBeamEntity( world )
    beam.effect = this
    beam
  }

  override def createBoltEntity( world : World ) : LightningBoltEntity = {
    val bolt = new LightningBoltEntity( world )
    bolt.effect = this
    bolt
  }

  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/lightning_beam.png" )
  val boltTexture = beamTexture
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/lightning_charge.png" )
}

trait LightningShootable {

  var pointsList : Seq[Vector3] = Seq()

  //Number of times this beam/bolt has been rendered. Used for rendering.
  var renderCount : Int = 0

}
class LightningBoltEntity(world : World) extends BaseBoltEntity(world) with LightningShootable {
  override def onUpdate() : Unit = {
    super.onUpdate()

    if ( world.isOnClient ) {
      pointsList = MidpointDisplacement.getBoltList
    }
  }
}
class LightningBeamEntity(world : World) extends BaseBeamEntity(world) with LightningShootable {
  override def depletionRate = 0.2d
}