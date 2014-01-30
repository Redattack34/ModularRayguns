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

package com.castlebravostudios.rayguns.plugins.te

import cofh.api.energy.IEnergyContainerItem
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.Optional
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.items.ChargableItem

@Optional.Interface(iface="cofh.api.energy.IEnergyContainerItem", modid="CoFHCore", striprefs=true)
trait RFItemPowerConnector extends IEnergyContainerItem {
  self : ChargableItem =>

  private val rfPowerMultiplier : Double = Config.rfPowerMultiplier

  @Optional.Method( modid = "CoFHCore" )
  def receiveEnergy(container : ItemStack, maxReceive : Int, simulate : Boolean ) : Int = {

    val maxReceivable = (maxReceive / rfPowerMultiplier).toInt
    val capacity = getChargeDepleted(container)
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, getMaxChargePerTick( container ) ) )

    if ( !simulate ) {
      addCharge( container, energyExtracted )
    }

    if ( maxReceive == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "CoFHCore" )
  def extractEnergy(container : ItemStack, maxExtract : Int, simulate : Boolean ) : Int = {
    val maxExtractable = (maxExtract / rfPowerMultiplier).toInt
    val stored = getChargeCapacity(container) - getChargeDepleted(container)
    val energyExtracted = Math.min( stored, Math.min( maxExtractable, getMaxChargePerTick( container ) ) )

    if ( !simulate ) {
      addCharge( container, -energyExtracted )
    }

    if ( maxExtract == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "CoFHCore" )
  def getEnergyStored(container : ItemStack) : Int = {
    val charge = getChargeCapacity(container) - getChargeDepleted(container)
    (charge * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "CoFHCore" )
  def getMaxEnergyStored(container : ItemStack) : Int =
    (getChargeCapacity(container) * rfPowerMultiplier).toInt
}