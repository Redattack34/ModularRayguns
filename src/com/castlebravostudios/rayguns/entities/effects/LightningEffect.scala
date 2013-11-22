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

trait LightningEffect extends Entity with BaseEffect {
  self : Shootable =>

  var pointsList : List[Vector3] = Nil

  //Number of times this beam/bolt has been rendered. Used for rendering.
  var renderCount : Int = 0

  def colourRed : Float = 1.0f
  def colourBlue : Float = 1.0f
  def colourGreen : Float = 0.5f

  def hitEntity( entity : Entity ) : Boolean = {
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shooter), 2f)
      true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }
}

class LightningBoltEntity(world : World) extends BaseBoltEntity(world) with LightningEffect with NoDuplicateCollisions
class LightningBeamEntity(world : World) extends BaseBeamEntity(world) with LightningEffect {
  override def lifetime = 5
}