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

package com.castlebravostudios.rayguns.items

//scalastyle:off underscore.import
import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.frames._
import com.castlebravostudios.rayguns.items.barrels._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.emitters._
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.api.ModuleRegistrationHelper
import com.castlebravostudios.rayguns.mod.Config
import ModuleRegistrationHelper.registerModule
import com.castlebravostudios.rayguns.items.frames.MantisFrame
import cpw.mods.fml.common.registry.GameRegistry
import GameRegistry.registerItem

object RaygunsItems {

  def registerItems : Unit = {
    registerMisc()
    registerEmitters()
    registerBatteries()
    registerLenses()
    registerAccessories()
    registerFrames()
    registerChambers()
    registerBarrels()
  }

  private def registerChambers() : Unit = {
    registerModule( LaserChamber, Config.chamberLaser )
    registerModule( HeatRayChamber, Config.chamberHeatRay )
    registerModule( LifeForceChamber, Config.chamberLifeForce )
    registerModule( FrostRayChamber, Config.chamberFrostRay )
    registerModule( FortifiedSunlightChamber, Config.chamberFortifiedSunlight )
    registerModule( ExplosiveChamber, Config.chamberExplosive )
    registerModule( DeathRayChamber, Config.chamberDeathRay )
    registerModule( EnderChamber, Config.chamberEnder )
    registerModule( LightningChamber, Config.chamberLightning )
    registerModule( ImpulseChamber, Config.chamberImpulse )
    registerModule( TractorChamber, Config.chamberTractor )
    registerModule( Tier1CuttingChamber, Config.chamberCuttingTier1 )
    registerModule( Tier2CuttingChamber, Config.chamberCuttingTier2 )
    registerModule( Tier3CuttingChamber, Config.chamberCuttingTier3 )
    registerModule( MatterTransporterChamber, Config.chamberMatterTransporter )
  }

  private def registerFrames() : Unit = {
    registerModule( MantisFrame, Config.mantisFrame )
    registerModule( FireflyFrame, Config.fireflyFrame )
  }

  private def registerAccessories() : Unit = {
    registerModule( HighEfficiencyWiring, Config.highEfficiencyWiring )
    registerModule( RefireCapacitor, Config.refireCapacitor )
    registerModule( SolarPanel, Config.solarPanel )
    registerModule( ChargeCapacitor, Config.chargeCapacitor )
  }

  private def registerLenses() : Unit = {
    registerModule( PreciseLens, Config.preciseLens )
    registerModule( WideLens, Config.wideLens )
  }

  private def registerBatteries() : Unit = {
    registerModule( BasicBattery, Config.basicBattery )
    registerModule( AdvancedBattery, Config.advancedBattery )
    registerModule( UltimateBattery, Config.ultimateBattery )
    registerModule( InfiniteBattery, Config.infiniteBattery )
  }

  private def registerEmitters() : Unit = {
    registerItem( Emitters.laserEmitter, "laserEmitter" )
    registerItem( Emitters.heatRayEmitter, "heatRayEmitter" )
    registerItem( Emitters.lifeForceEmitter, "lifeForceEmitter" )
    registerItem( Emitters.frostRayEmitter, "frostRayEmitter" )
    registerItem( Emitters.fortifiedSunlightEmitter, "fortifiedSunlightEmitter" )
    registerItem( Emitters.explosiveEmitter, "explosiveEmitter" )
    registerItem( Emitters.deathRayEmitter, "deathRayEmitter" )
    registerItem( Emitters.enderEmitter, "enderEmitter" )
    registerItem( Emitters.lightningEmitter, "lightningEmitter" )
    registerItem( Emitters.impulseEmitter, "impulseEmitter" )
    registerItem( Emitters.tractorEmitter, "tractorEmitter" )
    registerItem( Emitters.tier1CuttingEmitter, "tier1CuttingEmitter" )
    registerItem( Emitters.tier2CuttingEmitter, "tier2CuttingEmitter" )
    registerItem( Emitters.tier3CuttingEmitter, "tier3CuttingEmitter" )
    registerItem( Emitters.matterTransporterEmitter, "matterTransporterEmitter" )
  }

  private def registerMisc() : Unit = {
    registerItem( RayGun, "rayGun" )
    registerItem( BrokenGun, "brokenRayGun" )
    Config.recipeLibrary.getIngredientItems().foreach{ case (item, name) => registerItem( item, name ) }
  }

  private def registerBarrels() : Unit = {
    registerModule( BeamBarrel, Config.barrelBeam )
    registerModule( BlasterBarrel, Config.barrelBlaster )
  }
}