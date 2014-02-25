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

  def spawn( world : World, player : EntityLivingBase, shot : BaseBeamEntity ) : Unit =
    spawnSingleShot( shot, world, player )

  def spawnSingleShot( fx : BaseBeamEntity, world : World, player : EntityLivingBase ) : Unit = {
    fx.shooter = player
    val start = RaytraceUtils.getPlayerPosition(world, player)
    val end = RaytraceUtils.getPlayerTarget(world, player, fx.aimVector, fx.maxRange).toMinecraft( world )
    val hits = RaytraceUtils.rayTrace( world, player, start, end )(
        ( block, metadata, pos ) => fx.effect.canCollideWithBlock( fx, block, metadata, pos),
        ( entity ) => fx.effect.canCollideWithEntity( fx, entity ) )

    fx.setStart( start )
    fx.rotationPitch = if ( player.isSneaking() ) 0 else player.rotationPitch
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