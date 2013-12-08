package com.castlebravostudios.rayguns.utils

import net.minecraft.util.MathHelper
import net.minecraft.util.Vec3
import net.minecraft.world.World

case class Vector3( val x : Double, val y : Double, val z : Double ) {


  def this( vec : Vec3 ) = this( vec.xCoord, vec.yCoord, vec.zCoord )
  def toBlockPos : BlockPos =
    BlockPos( MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z) )

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

  def mult( n : Double ) = Vector3( x * n, y * n, z * n )

  def distanceTo( other : Vector3 ) : Double = this.subtract( other ).length

  def distSquared( other : Vector3 ) : Double = {
    val diff = this.subtract(other)
    diff.lengthSquared
  }

  def toList : List[Double] = List( x, y, z )

  def modify( f : Double => Double ) = Vector3( f(x), f(y), f(z) )

  def toMinecraft( world : World ) : Vec3 =
    world.getWorldVec3Pool().getVecFromPool(x, y, z)
}