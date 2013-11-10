package com.castlebravostudios.rayguns.utils

import net.minecraft.util.Vec3
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import net.minecraft.block.Block
import net.minecraft.util.MovingObjectPosition

object RaytraceUtils {

  type BlockPos = (Int, Int, Int)

  case class Vector3( val x : Double, val y : Double, val z : Double ) {
    def this( vec : Vec3 ) = this( vec.xCoord, vec.yCoord, vec.zCoord )
    def toBlockPos : BlockPos =
      (MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z) )

    def lengthSquared : Double = x * x + y * y + z * z
    def length : Double = MathHelper.sqrt_double( lengthSquared )

    def normalized : Vector3 = {
      val length = this.length
      if ( length <= 1.0E-4D ) Vector3( 0, 0, 0 )
      else Vector3( x / length, y / length, z / length )
    }

    def add( other : Vector3 ) : Vector3 =
      Vector3( x + other.x, y + other.y, z + other.z )

    def subtract( other : Vector3 ) : Vector3 =
      Vector3( x - other.x, y - other.y, z - other.z )

    def divideBy( n : Double ) =
      Vector3( x / n, y / n, z / n )

    def distanceTo( other : Vector3 ) : Double = this.subtract( other ).length

    def distSquared( other : Vector3 ) : Double = {
      val diff = this.subtract(other)
      diff.lengthSquared
    }

    def toList : List[Double] = List( x, y, z )

    def toMinecraft( world : World ) : Vec3 =
      world.getWorldVec3Pool().getVecFromPool(x, y, z)
  }

  /**
   * Algorithm taken from:
   * Amanatides, John & Woo, Andrew. A Fast Voxel Traversal Algorithm for Ray Tracing
   */
  def blocks( start : Vector3, end : Vector3 ) : Stream[BlockPos] = {
    val (x, y, z) = start.toBlockPos
    val endPos = end.toBlockPos
    val diff = end.subtract(start)
    val (stepX, stepY, stepZ) = (diff.x.signum, diff.y.signum, diff.z.signum)

    // Find the length of a ray such that one component equals the desired length
    def rayLength( component : Double, length : Double ) ={
      val l = diff.divideBy( component / length ).length
      if ( l.isNaN() || l.isInfinite() ) Double.MaxValue
      else l
    }

    val tDeltaX = rayLength( diff.x, 1.0 )
    val tDeltaY = rayLength( diff.y, 1.0 )
    val tDeltaZ = rayLength( diff.z, 1.0 )

    def blockSide( k : Int, stepK : Int ) = if ( stepK < 0 ) k else k + stepK
    val tMaxX = rayLength( diff.x, blockSide(x, stepX) - start.x )
    val tMaxY = rayLength( diff.y, blockSide(y, stepY) - start.y )
    val tMaxZ = rayLength( diff.z, blockSide(z, stepZ) - start.z )

    def blocksRec( x : Int, y : Int, z : Int, tMaxX : Double, tMaxY : Double, tMaxZ : Double ) : Stream[BlockPos] = {
      def branch = if      ( tMaxX < tMaxY && tMaxX < tMaxZ ) blocksRec( x + stepX, y, z, tMaxX + tDeltaX, tMaxY, tMaxZ )
                   else if ( tMaxY < tMaxX && tMaxY < tMaxZ ) blocksRec( x, y + stepY, z, tMaxX, tMaxY + tDeltaY, tMaxZ )
                   else if ( tMaxZ < tMaxX && tMaxZ < tMaxY ) blocksRec( x, y, z + stepZ, tMaxX, tMaxY, tMaxZ + tDeltaZ )
                   else throw new IllegalStateException
      (x, y, z) #:: branch
    }

    blocksRec(x, y, z, tMaxX, tMaxY, tMaxZ).takeWhile( _ != endPos ).append(Stream(endPos))
  }

  /**
   * Get all non-air blocks that could potentially intersect the line segment
   * between start and end.
   */
  def blocksHit( world : World, start : Vector3, end : Vector3 ) : Stream[(Block, Int, BlockPos)]=
    for {
      pos <- blocks( start, end )
      (x, y, z) = pos
      if (!world.isAirBlock(x, y, z) )
      block = Block.blocksList( world.getBlockId(x, y, z) )
      meta = world.getBlockMetadata(x, y, z)
    } yield (block, meta, pos)

  /**
   * Get all intersections with blocks matching f along the line segment between start
   * and end. This is lazily computed, so if you only use the first collision
   * no more will be calculated.
   */
  def rayTrace( world : World, start : Vec3, end : Vec3 )( f : (Block, Int) => Boolean ) : Stream[MovingObjectPosition] = {
    val filtered = blocksHit( world, new Vector3( start ), new Vector3( end ) ).filter{
      case ( b, m, _ ) => f( b, m )
    }

    for {
      (b, m, (x, y, z) ) <- filtered
      hit = b.collisionRayTrace(world, x, y, z, start, end)
      if ( hit != null )
    } yield hit
  }
}