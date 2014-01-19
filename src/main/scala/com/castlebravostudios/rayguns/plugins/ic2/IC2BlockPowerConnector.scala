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

@Optional.Interface(iface="ic2.api.energy.tile.IEnergySink", modid="IC2", striprefs=true)
trait IC2BlockPowerConnector extends TileEntity with IEnergySink {
  self : PoweredBlock =>

  var euBuffer : Double = 0
  var postedOnLoad = false
  def ic2PowerMultiplier = Config.ic2PowerMultiplier

  override def updateEntity() : Unit = {
    if ( !postedOnLoad && worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this))
      postedOnLoad = true
    }
    super.updateEntity()
  }

  override def invalidate() : Unit = {
    if ( worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
    }
    super.invalidate()
  }

  override def onChunkUnload() : Unit = {
    if ( worldObj.isOnServer ) {
      MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
    }
    super.onChunkUnload()
  }

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

  def injectEnergyUnits( directionFrom : ForgeDirection, amount : Double ) : Double = {
    println(s"Injecting... (current : $euBuffer " + worldObj.getWorldTime());
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
  def getMaxSafeInput() : Int = 32
  def acceptsEnergyFrom( emitter : TileEntity, direction : ForgeDirection ) : Boolean = true
}