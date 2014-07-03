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

package com.castlebravostudios.rayguns.entities

import scala.annotation.tailrec
import com.castlebravostudios.rayguns.utils.BlockPos
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import net.minecraft.entity.Entity
import net.minecraft.entity.IProjectile
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraft.world.World
import io.netty.buffer.ByteBuf

/**
 * Base class for beam entities. Most of this code is a poor translation
 * of the source of EntityThrowable, since I needed to modify something deep in
 * onUpdate. Not coincidentally, most of this code is really non-idiomatic Scala
 * and should not be taken as an example.
 */
class BaseBoltEntity( world : World ) extends BaseShootable( world ) with IProjectile {

  var depletionRate : Double = 0.05d

  def pitchOffset : Float = 0.5f
  def velocityMultiplier : Float = 1.5f

  protected var hitBlocks = Set[BlockPos]()
  protected var hitEntities = Set[Entity]()

  override def onUpdate() : Unit = {
    this.lastTickPosX = this.posX
    this.lastTickPosY = this.posY
    this.lastTickPosZ = this.posZ
    super.onUpdate()

    val startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)
    val endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)

    val hits = RaytraceUtils.rayTrace(world, this, startPos, endPos)(
        ( block, metadata, pos ) => effect.canCollideWithBlock( this, block, metadata, pos ) && !hitBlocks.contains(pos),
        ( entity ) => effect.canCollideWithEntity(this, entity ) && !hitEntities.contains( entity ) )
    applyHitsUntilStop(hits)

    this.posX += this.motionX
    this.posY += this.motionY
    this.posZ += this.motionZ

    if (this.isInWater())
    {
        for ( _ <- 0 until 4 )
        {
            val f4 = 0.25F
            this.worldObj.spawnParticle("bubble",
                this.posX - this.motionX * f4.doubleValue,
                this.posY - this.motionY * f4.doubleValue,
                this.posZ - this.motionZ * f4.doubleValue,
                this.motionX, this.motionY, this.motionZ)
        }
    }

    this.setPosition(this.posX, this.posY, this.posZ)

    charge -= depletionRate
    if ( charge <= 0 ) {
      setDead()
    }
  }

  /**
   * Applies the collisions in hits until the beam signals stop or hits is empty.
   */
  @tailrec
  private def applyHitsUntilStop( hits : Stream[MovingObjectPosition] ) : Unit =  hits match {
    case h #:: hs => if ( onImpact(h) ) () else applyHitsUntilStop(hs)
    case _ => ()
  }

  override def setSize( width : Float, height : Float ) : Unit = super.setSize( width, height )

  def onImpact( pos : MovingObjectPosition ) : Boolean = {
    effect.createImpactParticles( this, posX, posY, posZ )
    val shouldDie = pos.typeOfHit match {
      case MovingObjectType.ENTITY => {
        hitEntities += pos.entityHit
        effect.hitEntity( this, pos.entityHit )
      }
      case MovingObjectType.BLOCK => {
        hitBlocks += BlockPos(pos.blockX, pos.blockY, pos.blockZ)
        effect.hitBlock( this, pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
      }
      case MovingObjectType.MISS => true
    }

    if ( shouldDie ) setDead
    shouldDie
  }

  override def setDead() {
    effect match {
      case e : TriggerOnDeath => e.triggerAt(this, posX, posY, posZ)
      case _ => ()
    }
    super.setDead
  }

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeEntityToNBT(tag)
    tag.setDouble("depletionRate", depletionRate)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readEntityFromNBT(tag)
    depletionRate = tag.getDouble("depletionRate")
  }

  override def writeSpawnData( out : ByteBuf ) : Unit = {
    super.writeSpawnData(out)
    out.writeDouble( depletionRate )
  }

  override def readSpawnData( in : ByteBuf ) : Unit = {
    super.readSpawnData(in)
    depletionRate = in.readDouble()
  }

  override def isInRangeToRenderDist(limit : Double) : Boolean = {
    var d1 = this.boundingBox.getAverageEdgeLength() * 4.0D
    d1 *= 64.0D
    limit < d1 * d1
  }

  override def setThrowableHeading(xIn : Double, yIn : Double, zIn : Double, velocityMult : Float, scatterMult : Float ) : Unit = {
    var x = xIn
    var y = yIn
    var z = zIn
    val f2 = MathHelper.sqrt_double(x * x + y * y + z * z)
    x /= f2.doubleValue
    y /= f2.doubleValue
    z /= f2.doubleValue
    x += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    y += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    z += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    x *= velocityMult.doubleValue
    y *= velocityMult.doubleValue
    z *= velocityMult.doubleValue
    this.motionX = x
    this.motionY = y
    this.motionZ = z
    val f3 = MathHelper.sqrt_double(x * x + z * z)
    this.rotationYaw = (Math.atan2(x, z) * 180.0D / Math.PI).floatValue()
    this.prevRotationYaw = rotationYaw
    this.rotationPitch = (Math.atan2(y, f3.doubleValue) * 180.0D / Math.PI).floatValue()
    this.prevRotationPitch = rotationPitch
  }

  override def setPositionAndRotation2( x: Double, y : Double, z : Double, yaw : Float, pitch : Float, par9 : Int ) : Unit = {
    setPosition(x, y, z)
    setRotation(yaw, pitch)
  }

}
