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

package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import com.castlebravostudios.rayguns.mod.ModularRayguns

object LaserEffect extends BaseEffect {

  val effectKey = "Laser"
  val damageSourceKey = "laser"

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    entity.attackEntityFrom( getDamageSource( shootable ), 2f * shootable.charge.toFloat )
    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ): Unit = {
    for { _ <- 0 until 4 } spawnParticle(shootable, hitX, hitY, hitZ)
  }

  private def spawnParticle(shootable: Shootable, hitX: Double, hitY: Double, hitZ: Double): Unit = {
    shootable.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D)
  }

  val boltTexture = ModularRayguns.texture( "textures/bolts/laser_bolt.png" )
  val beamGlowTexture = ModularRayguns.texture( "textures/beams/beam_glow_laser.png" )
  val beamCoreTexture = ModularRayguns.texture( "textures/beams/beam_core_laser.png" )
  val chargeTexture = ModularRayguns.texture( "textures/effects/charge/laser_charge.png" )
}
