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
import com.castlebravostudios.rayguns.utils.Extensions._

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemSpade
import net.minecraft.util.ResourceLocation

class CuttingEffect( val key : String, val harvestLevel : Int, val powerMultiplier : Float ) extends BaseEffect {
  implicit class ShootableExtension(val shootable : Shootable) {
    def harvestPower( ) : Float = shootable.charge.toFloat * powerMultiplier
    def setHarvestPower( power : Float ) = {
      shootable.charge = power / powerMultiplier
    }
  }


  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = true

  def hitBlock( shootable : Shootable,  hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val block = worldObj.getBlock(hitX, hitY, hitZ)
    val meta = worldObj.getBlockMetadata(hitX, hitY, hitZ)

    val particleStr = s"blockcrack_${Block.getIdFromBlock(block)}_${meta}"
    for ( k <- 0 until 10 ) {
      worldObj.spawnParticle(particleStr, hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }

    if ( !canBreakBlock( shootable, hitX, hitY, hitZ, block ) ) { return true }
    else if ( shootable.worldObj.isOnServer ) {
      shootable.setHarvestPower( shootable.harvestPower - block.getBlockHardness(worldObj, hitX, hitY, hitZ) )
      val player = shootable.shooter match {
        case pl : EntityPlayer => pl
        case _ => null
      }
        if ( block.removedByPlayer(worldObj, player, hitX, hitY, hitZ) ) {
          block.onBlockDestroyedByPlayer(worldObj, hitX, hitY, hitZ, meta)
        }
        block.harvestBlock(worldObj, player, hitX, hitY, hitZ, meta)
        block.onBlockHarvested(worldObj, hitX, hitY, hitZ, meta, player)
      false
    }
    else true
  }

  def canBreakBlock( shootable : Shootable, x : Int, y : Int, z : Int, block : Block ) : Boolean = {
    val pick = harvestLevel match {
      case 0 => Item.pickaxeWood
      case 1 => Item.pickaxeStone
      case 2 => Item.pickaxeIron
      case 3 => Item.pickaxeDiamond
    }
    val pickCanHarvest = pick.canHarvestBlock(block) ||
      ItemPickaxe.blocksEffectiveAgainst.contains(block)

    val shovelCanHarvest = ItemSpade.field_150916_c.contains( block )

    val hardness = block.getBlockHardness(shootable.worldObj, x, y, z)
    if ( hardness == -1.0f ) {
      return false
    }

    hardness <= shootable.harvestPower && ( pickCanHarvest || shovelCanHarvest )
  }

  def effectKey : String = key
  val damageSourceKey = ""

  val boltTexture = new ResourceLocation( "rayguns", s"textures/bolts/cutting_bolt_t${harvestLevel}.png" )
  val beamTexture = new ResourceLocation( "rayguns", s"textures/beams/cutting_beam_t${harvestLevel}.png" )
  val chargeTexture = new ResourceLocation( "rayguns", s"textures/effects/charge/cutting_charge_t${harvestLevel}.png" )
}
