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

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack

object InfiniteBattery extends BaseRaygunModule with RaygunBattery {
  val moduleKey = "InfiniteBattery"
  val powerModifier = 1.0d;
  val nameSegmentKey = "rayguns.InfiniteBattery.segment"
  val maxCapacity = Integer.MAX_VALUE
  val maxChargePerTick = 16
  val ic2Tier = 4

  def createItem( id : Int ) : ItemModule = new ItemBattery( id, this )
    .setUnlocalizedName("rayguns.InfiniteBattery")
    .setTextureName("rayguns:battery_infinite")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  override def getChargeDepleted( item : ItemStack ) : Int = 0
  override def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = ()
  override def addCharge( item : ItemStack, delta : Int ) : Unit = ()
  override def getChargeString( item : ItemStack ) : String = I18n.getString("rayguns.infinityCharge")
}