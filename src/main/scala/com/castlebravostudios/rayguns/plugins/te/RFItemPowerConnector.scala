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

  private val maxPowerTransferPerTick = 2

  private val rfPowerMultiplier : Double = Config.rfPowerMultiplier

  @Optional.Method( modid = "CoFHCore" )
  def receiveEnergy(container : ItemStack, maxReceive : Int, simulate : Boolean ) : Int = {

    val maxReceivable = (maxReceive / rfPowerMultiplier).toInt
    val capacity = getChargeDepleted(container)
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, maxPowerTransferPerTick ))

    if ( !simulate ) {
      addCharge( container, energyExtracted )
    }

    if ( maxReceive == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "CoFHCore" )
  def extractEnergy(container : ItemStack, maxExtract : Int, simulate : Boolean ) : Int = {
    val maxExtractable = (maxExtract / rfPowerMultiplier).toInt
    val stored = getChargeCapacity(container) - getChargeDepleted(container)
    val energyExtracted = Math.min( stored, Math.min( maxExtractable, maxPowerTransferPerTick ))

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