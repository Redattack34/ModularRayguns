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
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.block.Block
import net.minecraft.block.BlockPistonBase
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntityPiston
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.init.Blocks

object TractorEffect extends BaseEffect {

  val effectKey = "Tractor"
  val damageSourceKey = "impulse"

  private def impulseStrength(shootable : Shootable ) = 1.5 * shootable.charge

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    entity.attackEntityFrom( getDamageSource( shootable ), 4f)

    val impulse = impulseVector(shootable).mult(impulseStrength(shootable))
    entity.addVelocity(impulse.x, impulse.y, impulse.z)
    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val hitPos = BlockPos( hitX, hitY, hitZ )
    val offset = hitOffset( side )
    if ( canPullBlock( shootable.worldObj, hitPos, offset ) ) {
      doPushBlocks( shootable.worldObj, hitPos, offset, side )
    }

    true
  }

  private def canPullBlock( worldObj : World, pos : BlockPos, offset : BlockPos ) : Boolean = {
    val BlockPos( x, y, z ) = pos
    val block = worldObj.getBlock( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )

    if ( worldObj.isAirBlock(x, y, z) || block.getMobilityFlag() == 1 ) true
    else if ( block == Blocks.obsidian) false
    else if ( block.getBlockHardness(worldObj, x, y, z ) == -1.0f ) false
    else if ( block.getMobilityFlag() == 2 ) false
    else if ( worldObj.getTileEntity( x, y, z ) != null ) false
    else true
  }

  private def doPushBlocks( worldObj : World, pos : BlockPos, offset : BlockPos, side : Int ) : Unit = {
    val BlockPos( x, y, z ) = pos
    val block = worldObj.getBlock( x, y, z )
    val meta = worldObj.getBlockMetadata( x, y, z )

    if ( worldObj.isAirBlock(x, y, z) || block.getMobilityFlag() == 1 ) return;

    worldObj.setBlockToAir(x, y, z)
    val BlockPos( x2, y2, z2 ) = pos.add(offset)
    worldObj.setBlock(x2, y2, z2, Blocks.piston_extension)
    worldObj.setTileEntity(x2, y2, z2, new TileEntityPiston( block, meta, side, true, false ) )
  }

  protected def impulseVector( shootable : Shootable ) : Vector3 = {
    val x = MathHelper.sin( shootable.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5;
    val y = 0.10D
    val z = -MathHelper.cos( shootable.rotationYaw * Math.PI.toFloat / 180.0f) * 0.5
    Vector3( x, y, z )
  }

  val boltTexture = ModularRayguns.texture( "textures/bolts/tractor_bolt.png" )
  val beamGlowTexture = ModularRayguns.texture( "textures/beams/beam_glow_tractor.png" )
  val beamCoreTexture = ModularRayguns.texture( "textures/beams/beam_core_tractor.png" )
  val chargeTexture = ModularRayguns.texture( "textures/effects/charge/tractor_charge.png" )
}
