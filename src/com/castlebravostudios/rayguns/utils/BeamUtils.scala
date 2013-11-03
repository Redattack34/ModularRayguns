package com.castlebravostudios.rayguns.utils

import scala.collection.JavaConverters.asScalaBufferConverter

import com.castlebravostudios.rayguns.entities.beams.LaserBeam
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

import cpw.mods.fml.client.FMLClientHandler
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.{MovingObjectPosition => TraceHit}
import net.minecraft.util.Vec3
import net.minecraft.world.World

object BeamUtils {

  def spawnSingleShot( fx : LaserBeam, world : World, player : EntityLivingBase ) : Unit = {
    if ( world.isOnServer ) return
    fx.shooter = player
    val hit = raytrace( world, player, 20.0d )
    if ( hit != null ) {
      fx.onImpact(hit)
      FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx)
    }
  }

  private def raytrace( world: World, player : EntityLivingBase, distance : Double ) : TraceHit = {
    val hitBlock = raytraceForBlocks( world, player, distance )
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

  private def raytraceForBlocks( world : World, player : EntityLivingBase, distance : Double ) : TraceHit = {
    val startVector = getPlayerPosition( world, player )
    val endVector = vecMult( player.getLookVec(), distance ).addVector(
        startVector.xCoord, startVector.yCoord, startVector.zCoord )
    world.clip(startVector, endVector, true);
  }

  private def getPlayerPosition( world : World, player : EntityLivingBase ) : Vec3 = {
    world.getWorldVec3Pool().getVecFromPool(
        player.posX, player.posY + player.getEyeHeight(), player.posZ)
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