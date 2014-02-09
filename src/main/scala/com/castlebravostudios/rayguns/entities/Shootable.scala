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

import cpw.mods.fml.common.registry.IThrowableEntity
import net.minecraft.entity.Entity
import net.minecraft.world.World
import net.minecraft.nbt.NBTTagCompound
import java.util.Random

trait Shootable extends Entity with IThrowableEntity {

  def charge : Double
  def charge_=( charge : Double ) : Unit

  private var _shooter : Entity = _
  private var shooterName : String = ""

  def shooter_=( shooter : Entity ) : Unit = {
    _shooter = shooter
    if ( shooter != null ) shooterName = shooter.getEntityName
  }

  def shooter : Entity = {
    if ( _shooter == null && shooterName != null && !shooterName.isEmpty ) {
      _shooter = this.worldObj.getPlayerEntityByName(shooterName)
    }
    _shooter
  }

  def getThrower : Entity = shooter
  def setThrower( e : Entity ) : Unit = shooter = e

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setString("ownerName", shooterName)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    shooterName = tag.getString("ownerName")
  }

  def random : Random
}