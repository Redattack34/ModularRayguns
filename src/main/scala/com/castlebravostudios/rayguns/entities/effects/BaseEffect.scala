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

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BoltRenderer
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos
import net.minecraft.block.Block
import net.minecraft.block.BlockFluid
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fluids.IFluidBlock
import net.minecraft.entity.player.EntityPlayer

trait BaseEffect {

  /**
   * Get the module key for this module. This key will be stored in the data
   * of the bolts and beams fired so that the effect can be looked up again
   * client-side.
   *
   * IMPORTANT NOTE: This key must not be changed once your plugin is released!
   */
  def effectKey : String

  /**
   * A collision has been detected against the given entity. Return true if the
   * bolt/beam should stop after this collision, or return false to indicate
   * that the bolt/beam penetrated through the entity.
   */
  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean

  /**
   * A collision has been detected against the given side of the block at the
   * given coords. Return true if the bolt/beam should stop after this collision,
   * or return false to indicate that the bolt/beam penetrated through the block.
   */
  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean

  /**
   * A collision has been detected at the given coords. Create impact particles
   * or perform other effects which require the precise coords.
   */
  def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()

  def canCollideWithBlock( shootable : Shootable, b : Block, metadata : Int, pos : BlockPos ) =
    if ( b.isInstanceOf[BlockFluid] || b.isInstanceOf[IFluidBlock] ) collidesWithLiquids(shootable)
    else true

  def canCollideWithEntity( shootable : Shootable, entity : Entity ) = !(entity == shootable.shooter)

  def collidesWithLiquids( shootable : Shootable ) : Boolean = false

  /**
   * Get the opposite side of the given side.
   */
  def invertSide( side : Int ) = side match {
      case 0 => 1
      case 1 => 0
      case 2 => 3
      case 3 => 2
      case 4 => 5
      case 5 => 4
    }

  def hitOffset( side : Int ) : BlockPos = side match {
    case 0 => BlockPos(0, -1, 0)
    case 1 => BlockPos(0, +1, 0)
    case 2 => BlockPos(0, 0, -1)
    case 3 => BlockPos(0, 0, +1)
    case 4 => BlockPos(-1, 0, 0)
    case 5 => BlockPos(+1, 0, 0)
  }

  /**
   * Adjust the coords to the block adjacent to the struck side.
   */
  def adjustCoords( x : Int, y : Int, z : Int, side : Int ) : BlockPos =
    BlockPos( x, y, z ).add( hitOffset( side ) )

  def boltTexture : ResourceLocation
  def beamTexture : ResourceLocation
  def lineTexture : ResourceLocation = BoltRenderer.lineBlackTexture
  def chargeTexture : ResourceLocation

  def createBeamEntity( world : World, player : EntityPlayer ) : BaseBeamEntity = {
    val beam = new BaseBeamEntity( world )
    beam.effect = this
    beam
  }

  def createBoltEntity( world : World, player : EntityPlayer ) : BaseBoltEntity = {
    val bolt = new BaseBoltEntity( world )
    bolt.effect = this
    bolt
  }
}