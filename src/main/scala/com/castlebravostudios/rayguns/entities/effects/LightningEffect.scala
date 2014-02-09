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
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.utils.Vector3
import com.castlebravostudios.rayguns.utils.MidpointDisplacement
import scala.collection.SortedSet
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.Extensions._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import net.minecraft.entity.monster.EntityCreeper
import com.castlebravostudios.rayguns.utils.BlockPos
import net.minecraft.util.ResourceLocation

object LightningEffect extends BaseEffect {

  val effectKey = "Lightning"

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    if ( entity.isInstanceOf[EntityCreeper] ) return true

    entity.attackEntityFrom(
      new EntityDamageSource("lightningRay", shootable.shooter), shootable.charge.toFloat * 4.0f )
      true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val shooter = shootable.shooter
    val BlockPos(x, y, z) = adjustCoords( hitX, hitY, hitZ, side )
    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( worldObj.isAirBlock(x, y, z) ) {
        worldObj.setBlock(x, y, z, Config.invisibleRedstone, side, 3)
      }
    }
    true
  }

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 4 ) {
      shootable.worldObj.spawnParticle("smoke", hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }
  }

  override def createBeamEntity( world : World, player : EntityPlayer ) : LightningBeamEntity = {
    val beam = new LightningBeamEntity( world )
    beam.effect = this
    beam
  }

  override def createBoltEntity( world : World, player : EntityPlayer ) : LightningBoltEntity = {
    val bolt = new LightningBoltEntity( world )
    bolt.effect = this
    bolt
  }

  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/lightning_beam.png" )
  val boltTexture = beamTexture
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/lightning_charge.png" )
}

trait LightningShootable {

  var pointsList : Seq[Vector3] = Seq()

  //Number of times this beam/bolt has been rendered. Used for rendering.
  var renderCount : Int = 0

}
class LightningBoltEntity(world : World) extends BaseBoltEntity(world) with LightningShootable {
  override def onUpdate() : Unit = {
    super.onUpdate()

    if ( world.isOnClient ) {
      pointsList = MidpointDisplacement.getBoltList
    }
  }
}
class LightningBeamEntity(world : World) extends BaseBeamEntity(world) with LightningShootable {
  override val depletionRate = 0.2d
}