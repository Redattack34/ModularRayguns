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

import com.castlebravostudios.rayguns.api.EffectRegistry
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import javax.swing.plaf.nimbus.Effect
import java.util.Random
import com.castlebravostudios.rayguns.utils.Logging
import io.netty.buffer.ByteBuf
import com.google.common.base.Charsets

abstract class BaseShootable( world : World ) extends Entity( world )
  with Shootable with IEntityAdditionalSpawnData with Logging {

  var effect : BaseEffect = _
  var charge : Double = 1.0d

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setDouble("charge", charge)
    tag.setString("effect", effect.effectKey )
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    charge = tag.getDouble("charge")

    val key = tag.getString( "effect" )
    initEffect( key )
  }

  def writeSpawnData( out : ByteBuf ) : Unit = {
    out.writeDouble( charge )

    val bytes = effect.effectKey.getBytes(Charsets.UTF_8)
    out.writeInt( bytes.length )
    out.writeBytes( bytes )
  }

  def readSpawnData( in : ByteBuf ) : Unit = {
    charge = in.readDouble()

    val byteCount = in.readInt()
    val key = in.readBytes( byteCount ).toString( Charsets.UTF_8 )
    initEffect( key )
  }

  private def initEffect( key : String ) : Unit = {
    val e = EffectRegistry.getEffect( key )
    e match {
      case Some(effect) => this.effect = effect
      case None => {
        error( s"Unknown effect key: $key" )
        setDead()
      }
    }
  }

  def entityInit() : Unit = ()

  def random : Random = this.rand
}