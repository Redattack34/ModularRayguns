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

package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.items.misc.GetFireInformationEvent
import com.castlebravostudios.rayguns.items.misc.PrefireEvent
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

object ChargeCapacitor extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "ChargeCapacitor"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.ChargeCapacitor.segment"

  def createItem( id : Int ) : ItemModule = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ChargeCapacitor")
    .setTextureName("rayguns:charge_capacitor")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  override def handleGetFireInformationEvent( event : GetFireInformationEvent ) : Unit = {
    super.handleGetFireInformationEvent(event)

    if ( event.player.getItemInUse() eq event.gun ) {
      val charge = RayGun.getChargePower( event.gun, event.player.getItemInUseCount() )
      event.powerMult *= Math.pow( charge, 0.444444444d )
      event.fireEvent = ChargeFireEvent( event.components, charge )
    }
  }

  override def handlePrefireEvent( event : PrefireEvent ) : Unit = {
    super.handlePrefireEvent(event)

    //Should only be able to fire after charging then releasing.
    event.canFire &= event.isOnStoppedUsing

    if ( event.player.getItemInUse() == null ) {
      event.player.setItemInUse( event.gun, Integer.MAX_VALUE )
    }
  }
}