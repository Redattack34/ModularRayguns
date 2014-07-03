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

import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.castlebravostudios.rayguns.api.EffectRegistry
import com.castlebravostudios.rayguns.utils.Vector3
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import io.netty.buffer.ByteBuf

class BaseBeamEntity(world : World) extends BaseShootable( world ) {

  def depletionRate : Double = 0.3d
  var length : Double = 0

  var maxRange : Int = 20

  var end : Vector3 = Vector3.zero

  ignoreFrustumCheck = true

  //I store the start point in the x/y/z coords to keep the spawn packet small
  //and because those fields are already stored/transmitted.
  def setStart( start : Vec3 ) : Unit = {
    setPosition(start.xCoord, start.yCoord, start.zCoord)
  }

  def onImpact( pos : MovingObjectPosition ) : Boolean = {
    pos.typeOfHit match {
      case MovingObjectType.ENTITY => effect.hitEntity( this, pos.entityHit )
      case MovingObjectType.BLOCK => effect.hitBlock( this, pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
    }
  }

  override def onUpdate() : Unit = {
    super.onUpdate

    charge -= depletionRate
    if ( charge <= 0 ) {
      setDead()
    }
  }

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeEntityToNBT(tag)
    tag.setInteger("maxRange", maxRange)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readEntityFromNBT(tag)
    maxRange = tag.getInteger("maxRange")
  }

  override def writeSpawnData( out : ByteBuf ) : Unit = {
    super.writeSpawnData(out)
    out.writeInt( maxRange )

    //This is sent already, but not precisely enough.
    out.writeFloat( rotationYaw )
    out.writeFloat( rotationPitch )

    out.writeFloat( length.toFloat )

    //Send the end point too, so we can spawn particles there client-side
    out.writeFloat( end.x.toFloat )
    out.writeFloat( end.y.toFloat )
    out.writeFloat( end.z.toFloat )
  }

  override def readSpawnData( in : ByteBuf ) : Unit = {
    super.readSpawnData(in)
    maxRange = in.readInt()

    rotationYaw = in.readFloat()
    rotationPitch = in.readFloat()

    length = in.readFloat()

    val endX = in.readFloat()
    val endY = in.readFloat()
    val endZ = in.readFloat()
    end = Vector3( endX, endY, endZ )

    effect.createImpactParticles( this, end.x, end.y, end.z )
  }
}