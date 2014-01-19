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

@Optional.Interface(iface="ic2.api.energy.tile.IEnergySink", modid="IC2", striprefs=true)
trait IC2BlockPowerConnector extends TileEntity with IEnergySink {
  self : PoweredBlock =>

  private[this] var postedOnLoad = false
  private[this] def ic2PowerMultiplier = Config.ic2PowerMultiplier

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
    chargeRequested * ic2PowerMultiplier
  }

  def injectEnergyUnits( directionFrom : ForgeDirection, amount : Double ) : Double = {
    val maxChargeReceivable = amount / ic2PowerMultiplier
    val remainingChargeCapacity = Math.min( chargeCapacity - chargeStored, maxChargeInput )
    val chargeExtracted = Math.min( remainingChargeCapacity, Math.min( maxChargeReceivable, amount ) ).toInt

    addCharge(chargeExtracted)

    amount - ( chargeExtracted * ic2PowerMultiplier )
  }

  def getMaxSafeInput() : Int = Integer.MAX_VALUE
  def acceptsEnergyFrom( emitter : TileEntity, direction : ForgeDirection ) : Boolean = true
}