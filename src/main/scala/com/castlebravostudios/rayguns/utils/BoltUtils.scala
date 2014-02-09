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

package com.castlebravostudios.rayguns.utils

import java.util.Random

import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.MathHelper
import net.minecraft.world.World

object BoltUtils {

  private final val rand = new Random();

  def spawn( world : World, player : EntityLivingBase, bolt : BaseBoltEntity ) : Unit =
    spawnNormal( world, bolt, player )

  def spawnNormal( world : World, bolt : BaseBoltEntity, shooter : EntityLivingBase ) : Unit = {
    if ( world.isOnClient ) return
    initBolt(bolt, shooter)
    setMotion(bolt, aimVector( shooter ) )
    world.spawnEntityInWorld(bolt)
  }

  def spawnPrecise( world : World, bolt : BaseBoltEntity, shooter : EntityLivingBase ) : Unit = {
    if ( world.isOnClient ) return
    initBolt(bolt, shooter)
    bolt.depletionRate = 0.025d
    setMotion(bolt, aimVector( shooter ) )
    world.spawnEntityInWorld(bolt)
  }

  def spawnScatter( world : World, shooter : EntityLivingBase, shots : Int, scatterFactor: Float )( bolt : () => BaseBoltEntity ) : Unit = {
    if ( world.isOnClient ) return
    val lookVec = aimVector( shooter )
    for ( _ <- 0 until shots ) {
      val shot = bolt()
      val shotVec = scatter( lookVec, scatterFactor )
      initBolt( shot, shooter )
      setMotion( shot, shotVec )
      world.spawnEntityInWorld( shot )
    }
  }

  private def aimVector( shooter : EntityLivingBase ) : Vector3 = {
    val look = Vector3( shooter.getLookVec )

    if ( shooter.isSneaking && !isCreativeFlying( shooter ) ) look.copy( y = 0 ) else look
  }

  private def scatter( vec : Vector3, factor : Float ) : Vector3 =
    vec.modify( _ + (getClampedGaussian() * factor) )
      .normalized

  private def getClampedGaussian() : Float =
    MathHelper.clamp_float(-2.0f, rand.nextGaussian().floatValue, 2.0f)

  private def toRadians(yaw: Float): Float = {
    yaw / 180.0F * Math.PI.floatValue
  }

  private def initBolt(bolt: BaseBoltEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
      bolt.shooter = shooter;
      bolt.setSize(0.25F, 0.25F);
      initPositionAngle(bolt, shooter)
      offsetInFrontOfShooter(bolt)
  }

  /**
   * Initializes the position and angles to be at the position of the shooter,
   * at eye level, in the direction the shooter is looking.
   */
  private def initPositionAngle(bolt: BaseBoltEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
    bolt.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight().doubleValue(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
  }

  /**
   * Offset the position based on the shooter's rotation so that it spawns just
   * in front of the shooter.
   */
  private def offsetInFrontOfShooter(bolt: BaseBoltEntity): Unit = {
    bolt.posX -= (MathHelper.cos(toRadians(bolt.rotationYaw)) * 0.16F).doubleValue();
    bolt.posY -= 0.10000000149011612D;
    bolt.posZ -= (MathHelper.sin(toRadians(bolt.rotationYaw)) * 0.16F).doubleValue();
    bolt.setPosition(bolt.posX, bolt.posY, bolt.posZ);
    bolt.yOffset = 0.0F;
  }

  /**
   * Sets the motion values to produce a given base velocity in the given
   * direction. Useful for changing the direction without affecting the velocity.
   */
  private def setMotion(bolt: BaseBoltEntity, motion : Vector3): Unit = {
    val baseVelocity = 0.4
    bolt.motionX = motion.x
    bolt.motionZ = motion.z
    bolt.motionY = motion.y
    bolt.setThrowableHeading(bolt.motionX, bolt.motionY, bolt.motionZ, bolt.velocityMultiplier, 1.0F);
  }

  private def isCreativeFlying( shooter : EntityLivingBase ) = shooter match {
      case p : EntityPlayer => p.capabilities.isFlying
      case _ => false
    }
}