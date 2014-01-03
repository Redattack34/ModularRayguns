package com.castlebravostudios.rayguns.utils

import scala.collection.JavaConverters.asScalaBufferConverter
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import cpw.mods.fml.client.FMLClientHandler
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.{MovingObjectPosition => TraceHit}
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraft.util.MathHelper
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.entities.TriggerOnDeath
import net.minecraft.block.Block
import scala.annotation.tailrec

object BeamUtils {

  val maxBeamLength = 40

  def spawnSingleShot( fx : BaseBeamEntity, world : World, player : EntityLivingBase ) : Unit = {
    fx.shooter = player
    val start = RaytraceUtils.getPlayerPosition(world, player)
    val end = RaytraceUtils.getPlayerTarget(world, player, maxBeamLength)
    val hits = RaytraceUtils.rayTrace( world, player, start, end )(
        ( block, metadata, pos ) => fx.effect.canCollideWithBlock( fx, block, metadata, pos),
        ( entity ) => fx.effect.canCollideWithEntity( fx, entity ) )

    fx.setStart( start )
    fx.rotationPitch = player.rotationPitch
    fx.rotationYaw = player.rotationYaw

    val target = applyHitsUntilStop(end, hits, fx)
    fx.length = target.distanceTo(start)

    fx.effect match {
      case t : TriggerOnDeath => t.triggerAt( fx, target.xCoord, target.yCoord, target.zCoord )
      case _ => ()
    }
    if ( world.isOnClient ) {
      world.spawnEntityInWorld(fx)
    }
  }

  /**
   * Applies the collisions in hits until the beam signals stop or hits is empty.
   * Returns the vector of the last collision or the target vector if no collision
   * stopped the beam.
   */
  @tailrec
  private def applyHitsUntilStop( target : Vec3, hits : Stream[TraceHit], fx : BaseBeamEntity) : Vec3 =  hits match {
    case h #:: hs => if ( fx.onImpact(h) ) h.hitVec else applyHitsUntilStop(target, hs, fx)
    case _ => target
  }
}