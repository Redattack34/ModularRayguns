/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.utils

import scala.collection.JavaConverters.collectionAsScalaIterableConverter

import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition

import net.minecraft.util.Vec3
import net.minecraft.world.World

object RaytraceUtils {

  type MOP = MovingObjectPosition

  /**
   * Algorithm taken from:
   * Amanatides, John & Woo, Andrew. A Fast Voxel Traversal Algorithm for Ray Tracing
   * http://www.cse.yorku.ca/~amana/research/grid.pdf
   */
  //scalastyle:off cyclomatic.complexity
  private def blocks( start : Vector3, end : Vector3 ) : Stream[BlockPos] = {
    val BlockPos(x, y, z) = start.toBlockPos
    val endPos = end.toBlockPos
    val diff = end.subtract(start)
    val (stepX, stepY, stepZ) = (diff.x.signum, diff.y.signum, diff.z.signum)

    // Find the length of a ray such that one component equals the desired length
    def rayLength( component : Double, length : Double ) : Double = {
      val l = diff.divideBy( component / length ).length
      if ( l.isNaN() || l.isInfinite() ) Double.MaxValue
      else l
    }

    val tDeltaX = rayLength( diff.x, 1.0 )
    val tDeltaY = rayLength( diff.y, 1.0 )
    val tDeltaZ = rayLength( diff.z, 1.0 )

    def blockSide( k : Int, stepK : Int ) : Int =
      if ( stepK < 0 ) k else k + stepK

    val tMaxX = rayLength( diff.x, blockSide(x, stepX) - start.x )
    val tMaxY = rayLength( diff.y, blockSide(y, stepY) - start.y )
    val tMaxZ = rayLength( diff.z, blockSide(z, stepZ) - start.z )

    val length = diff.length

    def blocksRec( x : Int, y : Int, z : Int, tMaxX : Double, tMaxY : Double, tMaxZ : Double ) : Stream[BlockPos] = {
      def branch : Stream[BlockPos] = if ( tMaxX > length && tMaxY > length && tMaxZ > length ) Stream.empty
                   else if ( tMaxX <= tMaxY && tMaxX <= tMaxZ ) blocksRec( x + stepX, y, z, tMaxX + tDeltaX, tMaxY, tMaxZ )
                   else if ( tMaxY <= tMaxX && tMaxY <= tMaxZ ) blocksRec( x, y + stepY, z, tMaxX, tMaxY + tDeltaY, tMaxZ )
                   else if ( tMaxZ <= tMaxX && tMaxZ <= tMaxY ) blocksRec( x, y, z + stepZ, tMaxX, tMaxY, tMaxZ + tDeltaZ )
                   else throw new IllegalStateException
      BlockPos(x, y, z) #:: branch
    }

    blocksRec(x, y, z, tMaxX, tMaxY, tMaxZ).takeWhile( _ != endPos ).append(Stream(endPos))
  }
  //scalastyle:on cyclomatic.complexity

  /**
   * Get all non-air blocks that could potentially intersect the line segment
   * between start and end which match f.
   */
  private def blocksHit( world : World, start : Vector3, end : Vector3 )( f : (Block, Int, BlockPos) => Boolean ) : Stream[(Block, Int, BlockPos)]=
    for {
      pos <- blocks( start, end )
      BlockPos(x, y, z) = pos
      if (!world.isAirBlock(x, y, z) )
      block = world.getBlock(x, y, z)
      meta = world.getBlockMetadata(x, y, z)
      if ( f( block, meta, pos ) )
    } yield (block, meta, pos)

  /**
   * Get all intersections with blocks along the line segment between start
   * and end. This is lazily computed, so if you only use the first collision
   * no more will be calculated. The returned stream will be in ascending
   * order of distance to the start vector.
   */
  def rayTraceBlocks( world : World, start : Vec3, end : Vec3 )( f : (Block, Int, BlockPos) => Boolean ) : Stream[MOP] = {
    for {
      (b, m, BlockPos(x, y, z) ) <- blocksHit( world, Vector3( start ), Vector3( end ) )(f)
      hit = b.collisionRayTrace(world, x, y, z, start, end)
      if ( hit != null )
    } yield hit
  }

  /**
   * Find all entities that a line segment between start and end could possibly
   * intersect with.
   */
  private def reachableEntities( world : World, owner : Entity, start : Vector3, end : Vector3 ) : Seq[Entity] = {
    var aabb = AxisAlignedBB.getBoundingBox(start.x, start.y, start.z,
        start.x, start.y, start.z);
    val diffLength = end.subtract(start).length * 1.1
    aabb = aabb.expand(diffLength, diffLength, diffLength)
    world.getEntitiesWithinAABBExcludingEntity(owner, aabb)
      .asInstanceOf[java.util.List[Entity]]
      .asScala.toSeq
  }

  private def collidableEntities( world : World, owner : Entity, start : Vector3, end : Vector3 )( f : Entity => Boolean ) : Seq[Entity] =
    for {
      e <- reachableEntities( world, owner, start, end )
      if ( e != null )
      if ( e.canBeCollidedWith() )
      if ( e.boundingBox != null )
      if ( f(e) )
    } yield e

  private def collide( target : Entity, start : Vec3, end : Vec3 ) : MOP = {
    val border = target.getCollisionBorderSize()
    val box = target.boundingBox.expand(border, border, border)
    val intercept = box.calculateIntercept(start, end)

    if ( intercept != null ) {
      val hit = new MOP( target )
      hit.hitVec = intercept.hitVec
      hit
    }
    else null
  }

  /**
   * Get all intersections with entities along the line segment between start
   * and end. This is NOT lazily computed, so all collisions will be calculated.
   * The returned seq will be in ascending order of distance to the start vector.
   */
  def rayTraceEntities( world : World, owner : Entity, start : Vec3, end : Vec3 )( f : Entity => Boolean ) : List[MOP] = {
    val collisions = for {
      entity <- collidableEntities(world, owner, Vector3( start ), Vector3( end ) )(f)
      mop = collide( entity, start, end )
      if ( mop != null )
    } yield mop

    collisions.sortBy( mop => mop.hitVec.squareDistanceTo(start) ).toList
  }

  /**
   * Get all intersections with blocks or entities along the line segment
   * between start and end. The returned stream will be in ascending order or
   * distance to the start vector.
   */
  def rayTrace( world : World, owner : Entity, start : Vec3, end : Vec3 )
    ( fB :  (Block, Int, BlockPos) => Boolean, fE : Entity => Boolean) : Stream[MOP] = {
    val blocks = rayTraceBlocks( world, start, end )(fB)
    val entities = rayTraceEntities(world, owner, start, end)(fE)

    def combine( blocks : Stream[MOP], entities : List[MOP]) : Stream[MOP] = (blocks, entities) match {
      case ( b #:: bs, e :: es ) => {
        val bLen = b.hitVec.squareDistanceTo(start)
        val eLen = e.hitVec.squareDistanceTo(start)

        if ( bLen < eLen ) b #:: combine( bs, entities )
        else               e #:: combine( blocks, es )
      }
      case ( bs, Nil ) => bs
      case ( Stream.Empty, es ) => es.toStream
    }

    combine( blocks, entities )
  }

  def getPlayerPosition( world : World, player : EntityLivingBase ) : Vec3 = {
    def toRadians(yaw: Float): Float = yaw / 180.0F * Math.PI.floatValue
    val offsetX = (MathHelper.cos(toRadians(player.rotationYaw)) * 0.08F).doubleValue()
    val offsetZ = (MathHelper.sin(toRadians(player.rotationYaw)) * 0.08F).doubleValue()

    val y = if ( world.isOnClient ) player.posY - 0.035 else player.posY + 1.62
    world.getWorldVec3Pool().getVecFromPool(
        player.posX - offsetX, y, player.posZ - offsetZ)
  }

  def getPlayerTarget( world : World, player : EntityLivingBase, aim : Vector3, distance : Double ) : Vector3 = {
    val playerPos = Vector3( getPlayerPosition(world, player) )
    val lookVector = aim.mult( distance ).add( playerPos )

    if ( player.isSneaking() ) lookVector.copy( y = playerPos.y )
    else lookVector
  }
}