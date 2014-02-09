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

import ic2.api.item.ISpecialElectricItem
import cpw.mods.fml.common.Optional
import net.minecraft.item.ItemStack
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import ic2.api.item.IElectricItemManager
import com.castlebravostudios.rayguns.items.ChargableItem

@Optional.Interface(iface="ic2.api.item.ISpecialElectricItem", modid="IC2", striprefs=true)
trait IC2ItemPowerConnector extends Item with ISpecialElectricItem {
  self : ChargableItem =>

  val ic2PowerMultiplier = Config.ic2PowerMultiplier

  @Optional.Method( modid = "IC2" )
  def canProvideEnergy( item : ItemStack ) : Boolean = true

  @Optional.Method( modid = "IC2" )
  def getChargedItemId( item : ItemStack ) : Int = this.itemID

  @Optional.Method( modid = "IC2" )
  def getEmptyItemId( item : ItemStack ) : Int = this.itemID

  @Optional.Method( modid = "IC2" )
  def getMaxCharge( item : ItemStack ) : Int = ( getMaxCharge( item ) * ic2PowerMultiplier ).toInt

  @Optional.Method( modid = "IC2" )
  def getTier( item : ItemStack ) : Int = getIC2Tier( item )

  @Optional.Method( modid = "IC2" )
  def getTransferLimit( item : ItemStack ): Int = ( getMaxChargePerTick( item ) * ic2PowerMultiplier).toInt

  @Optional.Method( modid = "IC2" )
  def getManager( item : ItemStack ) : IElectricItemManager = RaygunElectricItemHandler
}