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

package com.castlebravostudios.rayguns.plugins.ic2

import com.castlebravostudios.rayguns.blocks.PoweredBlock
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.ForgeDirection
import net.minecraftforge.common.MinecraftForge
import cpw.mods.fml.common.Optional
import ic2.api.energy.event.EnergyTileLoadEvent
import ic2.api.energy.event.EnergyTileUnloadEvent
import ic2.api.energy.tile.IEnergySink
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.nbt.NBTTagCompound
import cpw.mods.fml.common.Loader

@Optional.Interface(iface="ic2.api.energy.tile.IEnergySink", modid="IC2", striprefs=true)
trait IC2BlockPowerConnector extends TileEntity with IEnergySink {
  self : PoweredBlock =>

  import IC2BlockPowerConnector._

  var euBuffer : Double = 0
  var postedOnLoad = false
  def ic2PowerMultiplier = Config.ic2PowerMultiplier

  override def updateEntity() : Unit = {
    if ( ic2Loaded && !postedOnLoad && worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this))
      postedOnLoad = true
    }
    super.updateEntity()
  }

  override def invalidate() : Unit = {
    if ( ic2Loaded && worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
    }
    super.invalidate()
  }

  override def onChunkUnload() : Unit = {
    if ( ic2Loaded && worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
    }
    super.onChunkUnload()
  }

  @Optional.Method( modid = "IC2" )
  def demandedEnergyUnits() : Double = {
    val chargeRequested = Math.min( chargeCapacity - chargeStored, maxChargeInput )
    val buffer = euBuffer / ic2PowerMultiplier

    if ( buffer >= chargeRequested ) {
      val chargeFromBuffer = Math.min( buffer, chargeRequested ).toInt
      addCharge( chargeFromBuffer )
      euBuffer -= chargeFromBuffer * ic2PowerMultiplier
      0
    }
    else {
      32
    }
  }

  @Optional.Method( modid = "IC2" )
  def injectEnergyUnits( directionFrom : ForgeDirection, amount : Double ) : Double = {
    val maxAccepted = Math.min( amount, 32 - euBuffer )
    euBuffer += maxAccepted
    amount - maxAccepted
  }

  override def readFromNBT( tag : NBTTagCompound ) : Unit = {
    euBuffer = tag.getDouble("EUBuffer")
    super.readFromNBT(tag)
  }

  override def writeToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setDouble( "EUBuffer", euBuffer)
    super.writeToNBT(tag)
  }

  @Optional.Method( modid = "IC2" )
  def getMaxSafeInput() : Int = 32

  @Optional.Method( modid = "IC2" )
  def acceptsEnergyFrom( emitter : TileEntity, direction : ForgeDirection ) : Boolean = true
}
object IC2BlockPowerConnector {
  val ic2Loaded = Loader.isModLoaded("IC2")
}