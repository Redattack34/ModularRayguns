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

package com.castlebravostudios.rayguns.items.batteries

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.MoreInformation
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.items.ChargableItem

class ItemBattery( val battery : RaygunBattery ) extends ItemModule( battery ) with MoreInformation
  with ChargableItem with RFItemPowerConnector {

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] =
    List( battery.getChargeString( item ) )

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = battery.getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) : Boolean = getDisplayDamage( item ) > 0

  override def getMaxDamage( item: ItemStack ) : Int = battery.maxCapacity

  def getChargeCapacity( item : ItemStack ) : Int = battery.maxCapacity
  def getChargeDepleted( item : ItemStack ) : Int = battery.getChargeDepleted( item )
  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = battery.setChargeDepleted( item, depleted )
  def addCharge( item : ItemStack, delta : Int ) : Unit = battery.addCharge( item, delta )
  def getMaxChargePerTick( item : ItemStack ) : Int = battery.maxChargePerTick
}