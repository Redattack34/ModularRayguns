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

import cofh.api.energy.IEnergyHandler
import com.castlebravostudios.rayguns.blocks.PoweredBlock
import cpw.mods.fml.common.Optional
import com.castlebravostudios.rayguns.mod.Config
import net.minecraftforge.common.util.ForgeDirection

@Optional.Interface(iface="cofh.api.energy.IEnergyHandler", modid="CoFHCore", striprefs=true)
trait RFBlockPowerConnector extends IEnergyHandler {
  self : PoweredBlock =>

  val rfPowerMultiplier = Config.rfPowerMultiplier

  @Optional.Method( modid = "CoFHCore" )
  def receiveEnergy( from : ForgeDirection, maxReceive : Int, simulate : Boolean ) : Int = {
    val maxReceivable = (maxReceive / rfPowerMultiplier).toInt
    val capacity = chargeCapacity - chargeStored
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, maxChargeInput ) )

    if ( !simulate ) {
      addCharge( energyExtracted )
    }

    if ( maxReceive == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "CoFHCore" )
  def extractEnergy( from : ForgeDirection, maxExtract : Int, simulate : Boolean ) : Int = 0

  @Optional.Method( modid = "CoFHCore" )
  def canInterface( from : ForgeDirection ) : Boolean = true

  @Optional.Method( modid = "CoFHCore" )
  def getEnergyStored( from : ForgeDirection ) : Int = ( chargeStored * rfPowerMultiplier ).toInt

  @Optional.Method( modid = "CoFHCore" )
  def getMaxEnergyStored( from : ForgeDirection) : Int = ( chargeCapacity * rfPowerMultiplier ).toInt
}