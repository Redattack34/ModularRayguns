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

  def spawnSingleShot( fx : BaseBeamEntity with BaseEffect, world : World, player : EntityLivingBase ) : Unit = {
    fx.shooter = player
    val start = getPlayerPosition(world, player)
    val end = getPlayerTarget(world, player, maxBeamLength)
    val hits = RaytraceUtils.rayTrace( world, player, start, end )( fx.canCollideWithBlock _, fx.canCollideWithEntity _)
    val target = applyHitsUntilStop(end, hits, fx)

    if ( fx.isInstanceOf[TriggerOnDeath] ) {
      fx.asInstanceOf[TriggerOnDeath].triggerAt(target.xCoord, target.yCoord, target.zCoord)
    }
    fx.setStart( start )
    fx.length = target.distanceTo(start)
    fx.rotationPitch = player.rotationPitch
    fx.rotationYaw = player.rotationYaw
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
  private def applyHitsUntilStop( target : Vec3, hits : Stream[TraceHit], fx : BaseBeamEntity with BaseEffect ) : Vec3 =  hits match {
    case h #:: hs => if ( fx.onImpact(h) ) h.hitVec else applyHitsUntilStop(target, hs, fx)
    case _ => target
  }

  private def getPlayerPosition( world : World, player : EntityLivingBase ) : Vec3 = {
    def toRadians(yaw: Float): Float = yaw / 180.0F * Math.PI.floatValue
    val offsetX = (MathHelper.cos(toRadians(player.rotationYaw)) * 0.08F).doubleValue()
    val offsetZ = (MathHelper.sin(toRadians(player.rotationYaw)) * 0.08F).doubleValue()

    val y = if ( world.isOnClient ) player.posY - 0.035 else player.posY + 1.62
    world.getWorldVec3Pool().getVecFromPool(
        player.posX - offsetX, y, player.posZ - offsetZ)
  }

  private def getPlayerTarget( world : World, player : EntityLivingBase, distance : Double ) : Vec3 = {
    val playerPos = getPlayerPosition(world, player)
    vecMult( player.getLookVec(), distance ).addVector(
        playerPos.xCoord, playerPos.yCoord, playerPos.zCoord )
  }

  private def vecMult( vec : Vec3, factor : Double ) : Vec3 = {
    vec.xCoord *= factor
    vec.yCoord *= factor
    vec.zCoord *= factor
    vec
  }

}