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

package com.castlebravostudios.rayguns.items.emitters

import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import net.minecraft.item.Item

object Emitters {

  val laserEmitter = new Item( Config.emitterLaser )
    .setUnlocalizedName("rayguns.LaserEmitter")
    .setTextureName("rayguns:emitter_laser")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val heatRayEmitter = new Item( Config.emitterHeatRay )
    .setUnlocalizedName("rayguns.HeatRayEmitter")
    .setTextureName("rayguns:emitter_heat_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val frostRayEmitter = new Item( Config.emitterFrostRay )
    .setUnlocalizedName("rayguns.FrostRayEmitter")
    .setTextureName("rayguns:emitter_frost_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val lifeForceEmitter = new Item( Config.emitterLifeForce )
    .setUnlocalizedName("rayguns.LifeForceEmitter")
    .setTextureName("rayguns:emitter_life_force")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val fortifiedSunlightEmitter = new Item( Config.emitterFortifiedSunlight )
    .setUnlocalizedName("rayguns.FortifiedSunlightEmitter")
    .setTextureName("rayguns:emitter_fortified_sunlight")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val explosiveEmitter = new Item( Config.emitterExplosive )
    .setUnlocalizedName("rayguns.ExplosiveEmitter")
    .setTextureName("rayguns:emitter_explosive")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val deathRayEmitter = new Item( Config.emitterDeathRay )
    .setUnlocalizedName("rayguns.DeathRayEmitter")
    .setTextureName("rayguns:emitter_death_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val enderEmitter = new Item( Config.emitterEnder )
    .setUnlocalizedName("rayguns.EnderEmitter")
    .setTextureName("rayguns:emitter_ender")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val lightningEmitter = new Item( Config.emitterLightning )
    .setUnlocalizedName("rayguns.LightningEmitter")
    .setTextureName("rayguns:emitter_lightning")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val impulseEmitter = new Item( Config.emitterImpulse )
    .setUnlocalizedName("rayguns.ImpulseEmitter")
    .setTextureName("rayguns:emitter_impulse")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val tractorEmitter = new Item( Config.emitterTractor )
    .setUnlocalizedName("rayguns.TractorEmitter")
    .setTextureName("rayguns:emitter_tractor")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val tier1CuttingEmitter = new Item( Config.emitterTier1Cutting )
    .setUnlocalizedName("rayguns.Tier1CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t1")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val tier2CuttingEmitter = new Item( Config.emitterTier2Cutting )
    .setUnlocalizedName("rayguns.Tier2CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t2")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val tier3CuttingEmitter = new Item( Config.emitterTier3Cutting )
    .setUnlocalizedName("rayguns.Tier3CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t3")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val shrinkRayEmitter = new Item( Config.emitterShrinkRay )
    .setUnlocalizedName("rayguns.ShrinkRayEmitter")
    .setTextureName("rayguns:emitter_shrink_ray")
    .setCreativeTab( ModularRayguns.raygunsTab )

  val matterTransporterEmitter = new Item( Config.emitterMatterTransporter )
    .setUnlocalizedName("rayguns.MatterTransporterEmitter")
    .setTextureName("rayguns:emitter_matter_transporter")
    .setCreativeTab( ModularRayguns.raygunsTab )
}