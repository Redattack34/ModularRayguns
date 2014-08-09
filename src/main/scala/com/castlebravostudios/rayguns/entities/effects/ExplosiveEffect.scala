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
import com.castlebravostudios.rayguns.entities.TriggerOnDeath
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityTNTPrimed
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ExplosiveEffect extends BaseEffect with TriggerOnDeath with SimpleTextures {

  val effectKey = "Explosive"
  val damageSourceKey = ""

  /**
   * Fake 'EntityTNTPrimed' which is used to give the correct owner when causing
   * the explosion.
   */
  private class FakeTNTPrimed( shootable : Shootable ) extends EntityTNTPrimed( shootable.worldObj ) {
    override def getTntPlacedBy : EntityLivingBase = shootable.shooter match {
      case living : EntityLivingBase => living
      case _ => null //Should never happen
    }
  }

  def hitEntity( entity : Entity ) : Boolean = true
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true
  def createImpactParticles(hitX : Double, hitY : Double, hitZ: Double) : Unit = ()

  def triggerAt( shootable : Shootable, x : Double, y : Double, z : Double ) : Unit =
    if ( shootable.worldObj.isOnServer ) {
      shootable.worldObj.newExplosion(new FakeTNTPrimed( shootable ), x, y, z, 3.0f, false, true)
    }

  override def textureName : String = "explosive"
}
