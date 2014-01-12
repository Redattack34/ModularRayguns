package com.castlebravostudios.rayguns.utils

case class BlockPos( x : Int, y : Int, z : Int ) {
  def add( other : BlockPos ) = BlockPos( x + other.x, y + other.y, z + other.z )
  def sub( other : BlockPos ) = BlockPos( x - other.x, y - other.y, z - other.z )
}