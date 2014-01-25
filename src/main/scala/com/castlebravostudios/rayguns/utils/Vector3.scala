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