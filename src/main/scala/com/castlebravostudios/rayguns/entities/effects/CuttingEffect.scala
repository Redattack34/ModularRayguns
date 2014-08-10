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
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

class CuttingEffect( val key : String, val harvestLevel : Int, val powerMultiplier : Float )
  extends BaseEffect with SimpleTextures {
  implicit class ShootableExtension(val shootable : Shootable) {
    def harvestPower( ) : Float = shootable.charge.toFloat * powerMultiplier
    def setHarvestPower( power : Float ) : Unit = {
      shootable.charge = power / powerMultiplier
    }
  }


  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = true

  def hitBlock( shootable : Shootable,  hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val block = worldObj.getBlock(hitX, hitY, hitZ)
    val meta = worldObj.getBlockMetadata(hitX, hitY, hitZ)

    val particleStr = s"blockcrack_${Block.getIdFromBlock(block)}_${meta}"
    for { k <- 0 until 10 } {
      worldObj.spawnParticle(particleStr, hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }

    if ( !canBreakBlock( shootable, hitX, hitY, hitZ, block, meta ) ) { return true }
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

  private def canBreakBlock( shootable : Shootable, x : Int, y : Int, z : Int, block : Block, metadata : Int ): Boolean = {
    val pick = pickForHarvestLevel
    val pickCanHarvest = pick.getDigSpeed( pick.asStack(1), block, metadata ) > 1.0

    val shovel = shovelForHarvestLevel
    val shovelCanHarvest = shovel.getDigSpeed( shovel.asStack( 1 ), block, metadata ) > 1.0

    val hardness = block.getBlockHardness(shootable.worldObj, x, y, z)
    if ( hardness == -1.0f ) {
      return false
    }

    hardness <= shootable.harvestPower && ( pickCanHarvest || shovelCanHarvest )
  }

  private def shovelForHarvestLevel: Item = {
    harvestLevel match {
      case 0 => Items.wooden_shovel
      case 1 => Items.stone_shovel
      case 2 => Items.iron_shovel
      case 3 => Items.diamond_shovel
    }
  }

  private def pickForHarvestLevel: Item = {
    harvestLevel match {
      case 0 => Items.wooden_pickaxe
      case 1 => Items.stone_pickaxe
      case 2 => Items.iron_pickaxe
      case 3 => Items.diamond_pickaxe
    }
  }

  def effectKey : String = key
  val damageSourceKey = ""

  override def textureName : String = s"cutting_t${harvestLevel}"
}
