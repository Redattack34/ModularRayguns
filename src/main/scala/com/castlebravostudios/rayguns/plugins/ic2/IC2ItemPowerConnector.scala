package com.castlebravostudios.rayguns.plugins.ic2

import ic2.api.item.ISpecialElectricItem
import cpw.mods.fml.common.Optional
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import ic2.api.item.IElectricItemManager

@Optional.Interface(iface="ic2.api.item.ISpecialElectricItem", modid="IC2", striprefs=true)
trait IC2ItemPowerConnector extends Item with ISpecialElectricItem {

  import RaygunNbtUtils._

  val ic2PowerMultiplier = Config.ic2PowerMultiplier

  @Optional.Method( modid = "IC2" )
  def canProvideEnergy( item : ItemStack ) : Boolean = true

  @Optional.Method( modid = "IC2" )
  def getChargedItemId( item : ItemStack ) : Int = this.itemID

  @Optional.Method( modid = "IC2" )
  def getEmptyItemId( item : ItemStack ) : Int = this.itemID

  @Optional.Method( modid = "IC2" )
  def getMaxCharge( item : ItemStack ) = ( getMaxCharge( item ) * ic2PowerMultiplier ).toInt

  @Optional.Method( modid = "IC2" )
  def getTier( item : ItemStack ) = 1

  @Optional.Method( modid = "IC2" )
  def getTransferLimit( item : ItemStack ) = (2 * ic2PowerMultiplier).toInt

  @Optional.Method( modid = "IC2" )
  def getManager( item : ItemStack ) : IElectricItemManager = RaygunElectricItemHandler
}