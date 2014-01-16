package com.castlebravostudios.rayguns.plugins.te

import cofh.api.energy.IEnergyContainerItem
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.Optional
import com.castlebravostudios.rayguns.mod.Config

@Optional.Interface(iface="cofh.api.energy.IEnergyContainerItem", modid="Mekanism", striprefs=true)
trait RFItemPowerConnector extends IEnergyContainerItem {
  import RaygunNbtUtils._

  private val maxPowerTransferPerTick = 2

  private val rfPowerMultiplier : Double = Config.rfPowerMultiplier

  @Optional.Method( modid = "Mekanism" )
  def receiveEnergy(container : ItemStack, maxReceive : Int, simulate : Boolean ) : Int = {

    val maxReceivable = (maxReceive / rfPowerMultiplier).toInt
    val capacity = getChargeDepleted(container)
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, maxPowerTransferPerTick ))

    if ( !simulate ) {
      addCharge(energyExtracted, container)
    }

    if ( maxReceive == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "Mekanism" )
  def extractEnergy(container : ItemStack, maxExtract : Int, simulate : Boolean ) : Int = {
    val maxExtractable = (maxExtract / rfPowerMultiplier).toInt
    val stored = getMaxCharge(container) - getChargeDepleted(container)
    val energyExtracted = Math.min( stored, Math.min( maxExtract, maxPowerTransferPerTick ))

    if ( !simulate ) {
      addCharge(-energyExtracted, container)
    }

    if ( maxExtract == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "Mekanism" )
  def getEnergyStored(container : ItemStack) : Int = {
    val charge = getMaxCharge(container) - getChargeDepleted(container)
    (charge * rfPowerMultiplier).toInt
  }

  @Optional.Method( modid = "Mekanism" )
  def getMaxEnergyStored(container : ItemStack) : Int =
    (RaygunNbtUtils.getMaxCharge(container) * rfPowerMultiplier).toInt
}