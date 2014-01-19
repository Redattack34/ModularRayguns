package com.castlebravostudios.rayguns.plugins.ic2

import ic2.api.item.IElectricItemManager
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import ic2.api.item.ElectricItem
import net.minecraft.entity.EntityLivingBase
import cpw.mods.fml.common.Optional

@Optional.Interface(iface="ic2.api.item.IElectricItemManager", modid="IC2", striprefs=true)
object RaygunElectricItemHandler extends IElectricItemManager {

  import RaygunNbtUtils._

  val ic2PowerMultiplier = Config.ic2PowerMultiplier
  val maxPowerTransferPerTick = 2

  def charge( item : ItemStack, amount : Int, tier : Int, ignoreTransferLimit : Boolean, simulate : Boolean ) : Int = {
    val maxReceivable = (amount / ic2PowerMultiplier).toInt
    val capacity = getChargeDepleted( item )
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, maxPowerTransferPerTick ))

    if ( !simulate ) {
      addCharge(energyExtracted, item)
    }

    (energyExtracted * ic2PowerMultiplier).toInt
  }

  def discharge( item : ItemStack, amount : Int, tier : Int, ignoreTransferLimit : Boolean, simulate : Boolean ) : Int = {
    val maxExtractable = (amount / ic2PowerMultiplier).toInt
    val stored = getMaxCharge(item) - getChargeDepleted(item)
    val energyExtracted = Math.min( stored, Math.min( maxExtractable, maxPowerTransferPerTick ))

    if ( !simulate ) {
      addCharge(-energyExtracted, item)
    }

    (energyExtracted * ic2PowerMultiplier).toInt
  }

  def getCharge( item : ItemStack ) =
    ( getMaxCharge( item ) * ic2PowerMultiplier ).toInt
  def canUse( item : ItemStack, amount : Int ) =
    ElectricItem.manager.canUse(item, amount)
  def use( item : ItemStack, amount : Int, entity : EntityLivingBase ) =
    ElectricItem.manager.use( item, amount, entity )
  def chargeFromArmor( item : ItemStack, entity : EntityLivingBase ) =
    ElectricItem.manager.chargeFromArmor( item, entity )
  def getToolTip( item : ItemStack ) = null
}