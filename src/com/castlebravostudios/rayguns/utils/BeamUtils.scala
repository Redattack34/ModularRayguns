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

object BeamUtils {

  val maxBeamLength = 40

  def spawnSingleShot( fx : BaseBeamEntity with BaseEffect, world : World, player : EntityLivingBase ) : Unit = {
    fx.shooter = player
    val start = getPlayerPosition(world, player)
    val hit = raytrace( world, player, maxBeamLength, fx.collidesWithLiquids )
    if ( hit != null ) {
      fx.onImpact(hit)
    }
    fx.setStart( start )

    val target = if ( hit == null ) getPlayerTarget(world, player, maxBeamLength) else hit.hitVec
    if ( fx.isInstanceOf[TriggerOnDeath] ) {
      fx.asInstanceOf[TriggerOnDeath].triggerAt(target.xCoord, target.yCoord, target.zCoord)
    }
    fx.length = target.distanceTo(start)
    fx.rotationPitch = player.rotationPitch
    fx.rotationYaw = player.rotationYaw
    if ( world.isOnClient ) {
      world.spawnEntityInWorld(fx)
    }
  }

  private def raytrace( world: World, player : EntityLivingBase, distance : Double, hitLiquid : Boolean ) : TraceHit = {
    val hitBlock = raytraceForBlocks( world, player, distance, hitLiquid )
    val hitEntity = raytraceForEntities( world, player, distance )

    (hitBlock, hitEntity) match {
      case (null, entity) => entity
      case (block, null) => block
      case (block, entity) => getClosest( world, player, block, entity )
    }
  }

  private def getClosest( world : World, player : EntityLivingBase, block : TraceHit, entity : TraceHit ) : TraceHit = {
    val playerPos = getPlayerPosition( world, player )
    val blockDist = block.hitVec.distanceTo(playerPos)
    val entityDist = entity.hitVec.distanceTo(playerPos)

    if ( blockDist < entityDist ) block
    else entity
  }

  private def raytraceForEntities( world : World, player : EntityLivingBase, distance : Double ) : TraceHit = {
    val playerPos = getPlayerPosition( world, player )
    val playerTarget = getPlayerTarget( world, player, distance )

    val reachableEntities = getReachableEntities( world, player, distance )
    val candidates = for {
      entity <- reachableEntities
      if ( entity != null )
      if ( entity.canBeCollidedWith() )
      if ( entity.boundingBox != null )
      mop = intersect( entity, playerPos, playerTarget )
      if ( mop != null )
    } yield ( mop, mop.hitVec.distanceTo(playerPos) )

    if ( candidates.isEmpty ) null
    else candidates
      .filter{ case (_, dist) => dist < distance}
      .minBy( _._2 )._1
  }

  private def getReachableEntities( world : World, player : EntityLivingBase, distance : Double ) : Seq[Entity] = {
    val reach = 1.1 * distance
    val box = player.boundingBox.expand(reach, reach, reach)
    world.getEntitiesWithinAABBExcludingEntity(player, box).asScala.map{
      case e : Entity =>  e
      case _ => ;null : Entity
    }
  }

  private def intersect( entity : Entity, start : Vec3, end : Vec3 ) : TraceHit = {
    val border = entity.getCollisionBorderSize()
    val box = entity.boundingBox.expand(border, border, border)
    val intercept = box.calculateIntercept(start, end)

    if ( intercept != null ) {
      val hit = new TraceHit( entity )
      hit.hitVec = intercept.hitVec
      hit
    }
    else null
  }

  private def raytraceForBlocks( world : World, player : EntityLivingBase, distance : Double, hitLiquid : Boolean ) : TraceHit = {
    val startVector = getPlayerPosition( world, player )
    val endVector = vecMult( player.getLookVec(), distance ).addVector(
        startVector.xCoord, startVector.yCoord, startVector.zCoord )
    world.clip(startVector, endVector, hitLiquid);
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