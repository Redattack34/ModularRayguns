package com.castlebravostudios.rayguns.utils

import scala.collection.mutable.Buffer
import com.castlebravostudios.rayguns.mod.Config
import scala.collection.SortedSet

object MidpointDisplacement {

  private val minDetail = Config.minLightningDetail

  private def positionOrdering( start : Vector3 ) : Ordering[Vector3] = Ordering.fromLessThan( (vec1 : Vector3, vec2 : Vector3 ) =>
    vec1.distSquared(start) <= vec2.distSquared(start)
  )

  def createPositionList( start : Vector3, end : Vector3 ) : List[Vector3] = {

    val buffer = Buffer[Vector3]( start.subtract(start) )

    def positionRec( start : Vector3, end : Vector3, displace : Double ) : Unit = {
      if ( displace < minDetail ) {
        buffer += end
      }
      else {
        val x = ( start.x + end.x ) / 2 + (Math.random() - 0.5) * displace
        val y = ( start.y + end.y ) / 2 + (Math.random() - 0.5) * displace
        val z = ( start.z + end.z ) / 2 + (Math.random() - 0.5) * displace
        val mid = Vector3( x, y, z )
        positionRec( start, mid, displace / 2 )
        positionRec( mid, end, displace / 2 )
      }
    }

    positionRec( start.subtract(start), end.subtract(start), end.subtract(start).length / 8 )
    buffer.toList
  }
}