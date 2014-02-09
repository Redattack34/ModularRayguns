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

package com.castlebravostudios.rayguns.items.lenses

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.mod.ModularRayguns

object ChargeBeamLens extends BaseRaygunModule with RaygunLens with Chargable {
  val moduleKey = "ChargeBeamLens"
  val powerModifier = 1.2
  val nameSegmentKey = "rayguns.ChargeBeamLens.segment"

  def createItem( id : Int ) : ItemModule = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.ChargeBeamLens")
    .setTextureName("rayguns:lens_charge_beam")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)
}