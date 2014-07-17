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

import com.castlebravostudios.rayguns.entities.BoltRenderer
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.IPlantable
import net.minecraft.init.Blocks

object DeathRayEffect extends BaseEffect {

  val effectKey = "DeathRay"
  val damageSourceKey = "deathRay"

  def hitEntity( shootable : Shootable, hit : Entity ) : Boolean = {
    if ( hit.isInstanceOf[EntityLivingBase] ) {
      val living = hit.asInstanceOf[EntityLivingBase]

      if ( living.isEntityUndead() ) {
        living.heal(4)
      }
      else {
        hit.attackEntityFrom( getDamageSource( shootable ), 10f)
      }
    }

    false
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val block = worldObj.getBlock(hitX, hitY, hitZ)
    if ( canEdit(  shootable, hitX, hitY, hitZ, side ) && blockMatch.isDefinedAt( block ) ) {
      worldObj.setBlock(hitX, hitY, hitZ, blockMatch( block ) )
    }

    false
  }

  private def canEdit( shootable : Shootable, x : Int, y : Int, z : Int, side : Int ) : Boolean = {
    shootable.shooter match {
      case player : EntityPlayer => player.canPlayerEdit(x, y, z, side, RayGun.asStack )
      case _ => false
    }
  }

  private val blockMatch : PartialFunction[Block, Block] = {
    case i : IPlantable => Blocks.air
    case Blocks.grass => Blocks.dirt
    case Blocks.mycelium => Blocks.dirt
    case Blocks.leaves => Blocks.air
    case Blocks.vine => Blocks.air
  }

  override def subtractsColor = true

  val boltTexture = ModularRayguns.texture( "textures/bolts/death_ray_bolt.png" )
  val beamTexture = ModularRayguns.texture( "textures/beams/death_ray_beam.png" )
  val chargeTexture = ModularRayguns.texture( "textures/effects/charge/death_ray_charge.png" )
  override def lineTexture : ResourceLocation = BoltRenderer.lineWhiteTexture
}
