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

package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.entities.effects.CuttingEffect
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.misc.Tier1EmptyChamber
import com.castlebravostudios.rayguns.items.misc.Tier3EmptyChamber
import com.castlebravostudios.rayguns.items.misc.Tier2EmptyChamber

object Tier1CuttingChamber extends BaseChamber {
  val moduleKey = "Tier1CuttingChamber"
  val powerModifier = 2.0
  val shotEffect = new CuttingEffect( "Tier1Cutting", 1, 3.0f )
  val nameSegmentKey = "rayguns.Tier1CuttingChamber.segment"

   def createItem() : ItemModule = new ItemChamber( this,
       Emitters.tier1CuttingEmitter, Tier1EmptyChamber )
    .setUnlocalizedName("rayguns.Tier1CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t1")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
    registerPreciseShotHandler()
  }
}
object Tier2CuttingChamber extends BaseChamber {
  val moduleKey = "Tier2CuttingChamber"
  val powerModifier = 4.0
  val shotEffect = new CuttingEffect( "Tier2Cutting", 2, 4.5f )
  val nameSegmentKey = "rayguns.Tier2CuttingChamber.segment"

   def createItem() : ItemModule = new ItemChamber( this,
       Emitters.tier2CuttingEmitter, Tier2EmptyChamber )
    .setUnlocalizedName("rayguns.Tier2CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t2")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
    registerPreciseShotHandler()
  }
}
object Tier3CuttingChamber extends BaseChamber {
  val moduleKey = "Tier3CuttingChamber"
  val powerModifier = 6.0
  val shotEffect = new CuttingEffect( "Tier3Cutting", 3, 6.0f )
  val nameSegmentKey = "rayguns.Tier3CuttingChamber.segment"

   def createItem() : ItemModule = new ItemChamber( this,
       Emitters.tier3CuttingEmitter, Tier3EmptyChamber )
    .setUnlocalizedName("rayguns.Tier3CuttingChamber")
    .setTextureName("rayguns:chamber_cutting_t3")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  def registerShotHandlers() : Unit = {
    registerSingleShotHandlers()
    registerScatterShotHandler()
    registerChargedShotHandler()
    registerPreciseShotHandler()
  }
}