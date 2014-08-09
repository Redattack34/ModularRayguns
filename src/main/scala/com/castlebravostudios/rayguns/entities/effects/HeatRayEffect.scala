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
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.init.Blocks


object HeatRayEffect extends BaseEffect with SimpleTextures {

  val effectKey = "HeatRay"
  val damageSourceKey = "heatRay"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    hit.setFire( Math.round( shootable.charge.toFloat * 2 ) )
    hit.attackEntityFrom( getDamageSource( shootable ), shootable.charge.toFloat.round )

    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ): Boolean = {
    val BlockPos(centerX, centerY, centerZ) = adjustCoords( hitX, hitY, hitZ, side )
    val burnRadius = shootable.charge.toFloat.round

    for {
      x <- -burnRadius to burnRadius
      y <- -burnRadius to burnRadius
      z <- -burnRadius to burnRadius
      if ( x.abs + y.abs + z.abs < burnRadius )
    } {
      heatBlock( shootable, centerX + x, centerY + y, centerZ + z, side )
    }

    true
  }

  private def heatBlock( shootable : Shootable, x: Int, y: Int, z: Int, side : Int): AnyVal = {
    val worldObj = shootable.worldObj
    val shooter = shootable.shooter
    if ( worldObj.getBlock(x, y, z) == Blocks.ice ) {
      worldObj.setBlock( x, y, z, Blocks.water )
    }
    if ( shooter.isInstanceOf[EntityPlayer] &&
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Blocks.fire)
      }
    }
  }

  override def textureName : String = "heat"
}
