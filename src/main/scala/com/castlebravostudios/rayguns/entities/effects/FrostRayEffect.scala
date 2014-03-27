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
import com.castlebravostudios.rayguns.utils.BlockPos
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import com.castlebravostudios.rayguns.mod.ModularRayguns


object FrostRayEffect extends BaseEffect {

  val effectKey = "FrostRay"
  val damageSourceKey = "frostRay"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    hit.attackEntityFrom( getDamageSource( shootable ), shootable.charge.toFloat * 2 )

    val livingShooter = shootable.shooter match{
      case l : EntityLivingBase => l
      case _ => null
    }

    hit match {
      case living : EntityLivingBase => {
        Potion.moveSlowdown.affectEntity(livingShooter, living,
            shootable.charge.toFloat.round, 1.0d)
      }
    }

    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ): Boolean = {
    val BlockPos(centerX, centerY, centerZ) = adjustCoords( hitX, hitY, hitZ, side )
    val freezeRadius = shootable.charge.toFloat.round
    for {
      x <- -freezeRadius to freezeRadius
      y <- -freezeRadius to freezeRadius
      z <- -freezeRadius to freezeRadius
      if ( x.abs + y.abs + z.abs < freezeRadius )
    } {
      tryFreezeBlock( shootable.worldObj, centerX + x, centerY + y, centerZ + z )
    }

    true
  }

  private def tryFreezeBlock( worldObj : World, hitX: Int, hitY: Int, hitZ: Int  ): AnyVal = {
    val material = worldObj.getBlockMaterial(hitX, hitY, hitZ)
    val metadata = worldObj.getBlockMetadata(hitX, hitY, hitZ)
    val frozenBlock =
      if ( material == Material.water ) Block.ice
      else if ( material == Material.lava && metadata == 0 ) Block.obsidian
      else if ( material == Material.lava && metadata <= 4 ) Block.cobblestone
      else null

    if ( frozenBlock != null ) {
      worldObj.setBlock(hitX, hitY, hitZ, frozenBlock.blockID)
    }
    else if ( material.blocksMovement && worldObj.isAirBlock(hitX, hitY + 1, hitZ) ) {
      worldObj.setBlock( hitX, hitY + 1, hitZ, Block.snow.blockID )
    }
  }

  override def collidesWithLiquids(shootable : Shootable) : Boolean = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("snowballpoof", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  val boltTexture = ModularRayguns.texture( "textures/bolts/frost_bolt.png" )
  val beamTexture = ModularRayguns.texture( "textures/beams/frost_beam.png" )
  val chargeTexture = ModularRayguns.texture( "textures/effects/charge/frost_charge.png" )
}
