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

package com.castlebravostudios.rayguns.api.items

import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.utils.Extensions.ItemStackExtension
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.items.misc.PrefireEvent
import com.castlebravostudios.rayguns.items.misc.PostfireEvent

trait RaygunBattery extends RaygunModule {

  //scalastyle:off import.grouping
  import RaygunBattery.powerBase
  //scalastyle:on import.grouping

  val chargeDepleted = "ChargeDepleted"

  def maxCapacity : Int
  def maxChargePerTick : Int
  def ic2Tier : Int

  override def handlePrefireEvent( event : PrefireEvent ) : Unit = {
    val powerDrain = powerBase * event.powerMult
    if ( getChargeDepleted( event.gun ) + powerDrain > maxCapacity ) {
      event.canFire = false
    }
  }

  override def handlePostfireEvent( event : PostfireEvent ) : Unit = {
    val powerDrain = powerBase * event.powerMult
    addCharge( event.gun, -powerDrain.intValue() )
  }

  def getChargeDepleted( item : ItemStack ) : Int = {
    val tag = item.getTagCompoundSafe
    if ( !tag.hasKey( chargeDepleted ) ) {
      setChargeDepleted( item, 0 )
    }
    tag.getInteger( chargeDepleted )
  }

  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = {
    def clamp( min : Int, cur : Int, max : Int ) : Int =
      if ( cur < min ) min
      else if ( cur > max ) max
      else cur

    val actualCharge = clamp( 0, depleted, maxCapacity )

    item.getTagCompoundSafe.setInteger( chargeDepleted, actualCharge )
  }

  def addCharge( item : ItemStack, delta : Int ) : Unit =
    setChargeDepleted( item, getChargeDepleted( item ) - delta )

  def getChargeString( item : ItemStack ) : String = {
    ( (maxCapacity - getChargeDepleted( item ) ) + "/" + maxCapacity )
  }
}
object RaygunBattery {
  val powerBase = 10
}