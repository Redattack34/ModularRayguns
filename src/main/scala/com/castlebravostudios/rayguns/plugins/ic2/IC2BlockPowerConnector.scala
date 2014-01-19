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