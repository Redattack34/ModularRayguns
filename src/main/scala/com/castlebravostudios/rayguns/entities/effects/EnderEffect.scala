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
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.boss.IBossDisplayData
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import com.castlebravostudios.rayguns.mod.ModularRayguns

object EnderEffect extends BaseEffect {

  val effectKey = "Ender"
  val damageSourceKey = ""

  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = {
    if ( shootable.worldObj.isOnServer ) {
      doTeleport( shootable, entity )
    }
    true
  }

  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  override def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 16 ) {
        shootable.worldObj.spawnParticle("portal", hitX, hitY, hitZ,
            shootable.random.nextGaussian(), 0.0D, shootable.random.nextGaussian());
    }
  }

  private def getNewCoords( worldObj : World, entity : EntityLivingBase, x : Int, y : Int, z : Int) : BlockPos = {
    val start = worldObj.getWorldVec3Pool().getVecFromPool(entity.posX, entity.posY, entity.posZ)
    val end = worldObj.getWorldVec3Pool().getVecFromPool(x, y, z)
    val hit = RaytraceUtils.rayTraceBlocks(worldObj, start, end){
      (b, m, p) => b.getMaterial().blocksMovement() }.headOption

    hit match {
      case Some( mop ) => adjustCoords( mop.blockX, mop.blockY, mop.blockZ, mop.sideHit )
      case None => BlockPos(x, y, z)
    }
  }

  def getTeleportY( worldObj : World, x: Int, y: Int, z: Int) : Option[Int] = {
    var newY = y;
    while ( newY < y + 32 ) {
      if ( worldObj.isAirBlock(x, newY, z) && worldObj.isAirBlock(x, newY + 1, z) ) return Some( newY )
      newY += 1
    }
    None
  }

  def maxTeleportDistance( shootable : Shootable ) : Double = shootable.charge * 16

  def doTeleport( shootable : Shootable, entity: Entity ) : Unit = {
    if ( entity.isInstanceOf[IBossDisplayData] ) return
    if ( !entity.isInstanceOf[EntityLivingBase] ) return
    val living = entity.asInstanceOf[EntityLivingBase]

    val maxTeleportDistance = this.maxTeleportDistance(shootable)

    val x = (entity.posX + ( shootable.random.nextFloat() - 0.5 ) * maxTeleportDistance).toInt
    val z = (entity.posZ + ( shootable.random.nextFloat() - 0.5 ) * maxTeleportDistance).toInt
    val optY = getTeleportY( shootable.worldObj, x, living.posY.toInt, z )
    if ( optY.isEmpty ) return
    val y = optY.get

    val BlockPos(newX, newY, newZ) = getNewCoords( shootable.worldObj, living, x, y, z )
    val event = new EnderTeleportEvent( living, newX + (newX.signum * 0.5), newY,
        newZ + (newZ.signum * 0.5), 1.5f * shootable.charge.toFloat );

    if (!MinecraftForge.EVENT_BUS.post(event)) {
      living.setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
      living.prevPosX = event.targetX
      living.prevPosY = event.targetY
      living.prevPosZ = event.targetZ
      living.fallDistance = 0.0F;
      living.attackEntityFrom(DamageSource.fall, event.attackDamage);
    }
  }

  val boltTexture = ModularRayguns.texture( "textures/bolts/ender_bolt.png" )
  val beamTexture = ModularRayguns.texture( "textures/beams/ender_beam.png" )
  val chargeTexture = ModularRayguns.texture( "textures/effects/charge/ender_charge.png" )
}
