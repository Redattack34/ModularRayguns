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

import ic2.api.item.IElectricItemManager
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.mod.Config
import ic2.api.item.ElectricItem
import net.minecraft.entity.EntityLivingBase
import cpw.mods.fml.common.Optional
import com.castlebravostudios.rayguns.items.ChargableItem

@Optional.Interface(iface="ic2.api.item.IElectricItemManager", modid="IC2", striprefs=true)
object RaygunElectricItemHandler extends IElectricItemManager {

  val ic2PowerMultiplier = Config.ic2PowerMultiplier

  def charge( item : ItemStack, amount : Int, tier : Int, ignoreTransferLimit : Boolean, simulate : Boolean ) : Int = {
    val chargableItem = chargable( item )
    if ( tier < chargableItem.getIC2Tier( item ) ) { return 0 }

    val maxReceivable = (amount / ic2PowerMultiplier).toInt
    val capacity = chargableItem.getChargeCapacity( item )
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, chargableItem.getMaxChargePerTick( item ) ) )

    if ( !simulate ) {
      chargableItem.addCharge( item, energyExtracted )
    }

    (energyExtracted * ic2PowerMultiplier).toInt
  }

  def discharge( item : ItemStack, amount : Int, tier : Int, ignoreTransferLimit : Boolean, simulate : Boolean ) : Int = {
    val chargableItem = chargable( item )
    if ( tier < chargableItem.getIC2Tier( item ) ) { return 0 }

    val maxExtractable = (amount / ic2PowerMultiplier).toInt
    val stored = chargableItem.getChargeCapacity(item) - chargableItem.getChargeDepleted(item)
    val energyExtracted = Math.min( stored, Math.min( maxExtractable, chargableItem.getMaxChargePerTick( item ) ) )

    if ( !simulate ) {
      chargableItem.addCharge( item, -energyExtracted )
    }

    (energyExtracted * ic2PowerMultiplier).toInt
  }

  def getCharge( item : ItemStack ) : Int =
    ( chargable( item ).getChargeCapacity( item ) * ic2PowerMultiplier ).toInt
  def canUse( item : ItemStack, amount : Int ) : Boolean =
    ElectricItem.manager.canUse(item, amount)
  def use( item : ItemStack, amount : Int, entity : EntityLivingBase ) : Boolean =
    ElectricItem.manager.use( item, amount, entity )
  def chargeFromArmor( item : ItemStack, entity : EntityLivingBase ) : Unit =
    ElectricItem.manager.chargeFromArmor( item, entity )
  def getToolTip( item : ItemStack ) : String = null

  private def chargable( item : ItemStack ) = item.getItem match {
    case chargable : ChargableItem => chargable
  }
}