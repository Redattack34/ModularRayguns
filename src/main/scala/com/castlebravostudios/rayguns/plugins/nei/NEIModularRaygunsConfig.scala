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

package com.castlebravostudios.rayguns.plugins.nei

//scalastyle:off underscore.import

import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui
import com.castlebravostudios.rayguns.items.RaygunsBlocks
import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.barrels._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.frames._
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions

import codechicken.nei.api.API
import codechicken.nei.api.IConfigureNEI
import codechicken.nei.recipe.DefaultOverlayHandler
import cpw.mods.fml.common.Mod
//scalastyle:on

class NEIModularRaygunsConfig extends IConfigureNEI {

  def loadConfig() : Unit = {
    API.hideItem( RaygunsBlocks.invisibleRedstone.asStack )
    API.hideItem( RayGun.asStack )
    API.hideItem( BrokenGun.asStack )

/*    API.addSetRange("Modular Rayguns", mainItemRange)
    API.addSetRange("Modular Rayguns.Accessories", accessoryItemRange)
    API.addSetRange("Modular Rayguns.Barrels", barrelItemRange)
    API.addSetRange("Modular Rayguns.Batteries", batteryItemRange)
    API.addSetRange("Modular Rayguns.Frames", frameItemRange)
    API.addSetRange("Modular Rayguns.Chambers", chamberItemRange)
    API.addSetRange("Modular Rayguns.Emitters", emitterItemRange)
    API.addSetRange("Modular Rayguns.Lenses", lensItemRange)*/

    API.registerRecipeHandler( new NEILensGrinderRecipeManager )
    API.registerUsageHandler( new NEILensGrinderRecipeManager )
    API.registerGuiOverlay( classOf[LensGrinderGui], NEIModularRaygunsConfig.recipeKey )
    API.registerGuiOverlayHandler( classOf[LensGrinderGui],
        new DefaultOverlayHandler, NEIModularRaygunsConfig.recipeKey )
  }

  def getName : String = ModularRayguns.getClass().getAnnotation(classOf[Mod]).name()
  def getVersion : String = ModularRayguns.getClass().getAnnotation(classOf[Mod]).version()
/*
  private def mainItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    range.add(RaygunsBlocks.gunBench)
    range.add(RaygunsBlocks.lensGrinder)

    Config.recipeLibrary.getIngredientItems().map( _._1 ).foreach( range.add )

    range
  }

  private def chamberItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, LaserChamber )
    addModule( range, HeatRayChamber )
    addModule( range, LightningChamber )
    addModule( range, Tier1CuttingChamber )

    addModule( range, FrostRayChamber )
    addModule( range, LifeForceChamber )
    addModule( range, EnderChamber )
    addModule( range, FortifiedSunlightChamber )
    addModule( range, ImpulseChamber )
    addModule( range, TractorChamber )
    addModule( range, MatterTransporterChamber )
    addModule( range, Tier2CuttingChamber )

    addModule( range, DeathRayChamber )
    addModule( range, ExplosiveChamber )
    addModule( range, Tier3CuttingChamber )
    range
  }

  private def lensItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, PreciseLens )
    addModule( range, WideLens )
    range
  }

  private def frameItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, FireflyFrame )
    addModule( range, MantisFrame )
    range
  }

  private def accessoryItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, HighEfficiencyWiring )
    addModule( range, RefireCapacitor )
    addModule( range, SolarPanel )
    addModule( range, ChargeCapacitor )
    range
  }

  private def batteryItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, BasicBattery )
    addModule( range, AdvancedBattery )
    addModule( range, UltimateBattery )
    addModule( range, InfiniteBattery )
    range
  }

  private def barrelItemRange: MultiItemRange = {
    val range = new MultiItemRange()
    addModule( range, BeamBarrel )
    addModule( range, BlasterBarrel )
    range
  }

  private def emitterItemRange: MultiItemRange = {
    val range = new MultiItemRange()

    range.add( Emitters.laserEmitter )
    range.add( Emitters.heatRayEmitter )
    range.add( Emitters.lightningEmitter )
    range.add( Emitters.tier1CuttingEmitter )

    range.add( Emitters.frostRayEmitter )
    range.add( Emitters.lifeForceEmitter )
    range.add( Emitters.enderEmitter )
    range.add( Emitters.fortifiedSunlightEmitter )
    range.add( Emitters.impulseEmitter )
    range.add( Emitters.tractorEmitter )
    range.add( Emitters.matterTransporterEmitter )
    range.add( Emitters.tier2CuttingEmitter )

    range.add( Emitters.deathRayEmitter )
    range.add( Emitters.explosiveEmitter )
    range.add( Emitters.tier3CuttingEmitter )

    range
  }

  private def addModule( range: MultiItemRange, module : RaygunModule ) =
    module.item.foreach( range.add ) */
}
object NEIModularRaygunsConfig {
  val recipeKey = "LensGrinder"
}