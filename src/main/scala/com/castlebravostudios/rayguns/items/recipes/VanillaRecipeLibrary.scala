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

package com.castlebravostudios.rayguns.items.recipes

//scalastyle:off underscore.import

import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.items._
import com.castlebravostudios.rayguns.items.Blocks
import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.barrels._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.bodies._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.ScalaShapedRecipeFactory

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

import cpw.mods.fml.common.registry.GameRegistry

//scalastyle:on

object VanillaRecipeLibrary extends RecipeLibrary {

  def registerRecipes() : Unit = {
    registerAccessories()
    registerBatteries()
    registerBodies()
    registerChambers()
    registerEmitters()
    registerLenses()
    registerBarrels()
    registerGainMedia()
    registerDustedGlass()
    registerDopedGlass()
    registerDiodes()
    registerHeatSinks()
    registerCasings()
    registerMisc()
  }

  private def registerAccessories() = {
    addModuleShaped( ExtendedBattery,
      "CCC",
      "III",
      "CCC",
      ( 'C' -> Block.ice ),
      ( 'I' -> Item.ingotIron ) )
    addModuleShaped( RefireCapacitor,
      "IPI",
      "IPI",
      " S ",
      ( 'S' -> Shutter ),
      ( 'I' -> Item.ingotIron ),
      ( 'P' -> Item.paper ) )
    addModuleShaped( SolarPanel,
      "GGG",
      "III",
      "RRR",
      ( 'I' -> Item.ingotIron ),
      ( 'R' -> Block.blockRedstone ),
      ( 'G' -> Block.glass ) )
    addModuleShaped( ChargeCapacitor,
      "GLG",
      "GLG",
      "B B",
      ( 'G' -> Item.ingotGold ),
      ( 'L' -> Block.glass ),
      ( 'B' -> BasicBattery ) )
  }

  private def registerBatteries() = {
    def addBatteryRecipe( battery : RaygunBattery, core : Any ) : Unit = {
      addModuleShaped( battery,
        "IGI",
        "IRI",
        "IRI",
        ( 'G' -> Item.ingotGold ),
        ( 'I' -> Item.ingotIron ),
        ( 'R' -> core ) )
    }

    addBatteryRecipe( BasicBattery, Block.blockRedstone )
    addBatteryRecipe( AdvancedBattery, BasicBattery )
    addBatteryRecipe( UltimateBattery, AdvancedBattery )
  }

  private def registerBodies() = {
    def addBodyRecipe( body : RaygunBody, core : Any ) : Unit = {
      addModuleShaped( body,
        "IR ",
        " IR",
        " LI",
        ( 'L' -> Block.lever ),
        ( 'R' -> core ),
        ( 'I' -> Item.ingotIron ) )
    }
    addBodyRecipe( FireflyBody, Item.dyePowder.asStack( 1, 1 ) )
    addBodyRecipe( MantisBody, Item.dyePowder.asStack( 1, 2 ) )
  }

  private def registerChambers() = {
    def registerChamber( chamber : RaygunChamber, emitter : Item, medium : Item, diode : Item, casing : Item ) : Unit = {
      addModuleShaped( chamber,
        "CDC",
        "MME",
        "CDC",
        ( 'D' -> diode ),
        ( 'C' -> casing ),
        ( 'M' -> medium ),
        ( 'E' -> emitter ) )
    }
    def registerT1Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier1GainMedium, Tier1Diode, Item.ingotIron )
    def registerT2Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier2GainMedium, Tier2Diode, Item.ingotGold )
    def registerT3Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier3GainMedium, Tier3Diode, Item.diamond )

    registerT1Chamber( Tier1CuttingChamber, Emitters.tier1CuttingEmitter)
    registerT1Chamber( HeatRayChamber, Emitters.heatRayEmitter)
    registerT1Chamber( LaserChamber, Emitters.laserEmitter)
    registerT1Chamber( LightningChamber, Emitters.lightningEmitter)

    registerT2Chamber( Tier2CuttingChamber, Emitters.tier2CuttingEmitter)
    registerT2Chamber( FortifiedSunlightChamber, Emitters.fortifiedSunlightEmitter)
    registerT2Chamber( FrostRayChamber, Emitters.frostRayEmitter)
    registerT2Chamber( ImpulseChamber, Emitters.impulseEmitter)
    registerT2Chamber( LifeForceChamber, Emitters.lifeForceEmitter)
    registerT2Chamber( TractorChamber, Emitters.tractorEmitter)
    registerT2Chamber( MatterTransporterChamber, Emitters.matterTransporterEmitter )
    registerT2Chamber( EnderChamber, Emitters.enderEmitter )

    registerT3Chamber( Tier3CuttingChamber, Emitters.tier3CuttingEmitter)
    registerT3Chamber( DeathRayChamber, Emitters.deathRayEmitter)
    registerT3Chamber( ExplosiveChamber, Emitters.explosiveEmitter)
  }

  private def registerEmitters() = {
    def registerEmitter( emitter : Item, core : AnyRef, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit = {
      addShaped( emitter.asStack,
        "ITI",
        "LDR",
        "IBI",
        'I' -> Item.ingotIron,
        'D' -> core,
        'T' -> top,
        'R' -> right,
        'B' -> bottom,
        'L' -> left )
    }
    def registerT1Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit =
      registerEmitter( emitter, Tier1Diode, top, right, bottom, left )
    def registerT2Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit =
      registerEmitter( emitter, Tier2Diode, top, right, bottom, left )
    def registerT3Emitter( emitter : Item, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Unit =
      registerEmitter( emitter, Tier3Diode, top, right, bottom, left )

    registerT1Emitter( Emitters.laserEmitter, Item.redstone, Item.redstone, Item.redstone, Item.redstone )
    registerT1Emitter( Emitters.heatRayEmitter, Item.coal, Item.bucketLava, Item.coal, Item.bucketLava )
    registerT1Emitter( Emitters.lightningEmitter, Block.blockIron, Block.blockRedstone, Block.blockIron, Block.blockRedstone )
    registerT1Emitter( Emitters.tier1CuttingEmitter, Item.pickaxeStone, Item.shovelStone, Item.pickaxeStone, Item.shovelStone )

    registerT2Emitter( Emitters.frostRayEmitter, Block.ice, Block.blockSnow, Block.ice, Block.blockSnow )
    registerT2Emitter( Emitters.lifeForceEmitter, Item.speckledMelon, Item.ghastTear, Item.speckledMelon, Item.ghastTear )
    registerT2Emitter( Emitters.fortifiedSunlightEmitter, Block.wood, Block.wood, Block.wood, Block.wood )
    registerT2Emitter( Emitters.enderEmitter, Item.enderPearl, Item.enderPearl, Item.enderPearl, Item.enderPearl )
    registerT2Emitter( Emitters.impulseEmitter, Block.pistonBase, Block.pistonBase, Block.pistonBase, Block.pistonBase )
    registerT2Emitter( Emitters.tractorEmitter, Block.pistonStickyBase, Block.pistonStickyBase, Block.pistonStickyBase, Block.pistonStickyBase )
    registerT2Emitter( Emitters.matterTransporterEmitter, Item.enderPearl, Block.pistonBase, Item.enderPearl, Block.pistonBase )
    registerT2Emitter( Emitters.tier2CuttingEmitter, Item.pickaxeIron, Item.shovelIron, Item.pickaxeIron, Item.shovelIron )

    val witherSkull = Item.skull.asStack( 1, 1 )
    registerT3Emitter( Emitters.deathRayEmitter, witherSkull, witherSkull, witherSkull, witherSkull )
    registerT3Emitter( Emitters.explosiveEmitter, Block.tnt, Block.tnt, Block.tnt, Block.tnt )
    registerT3Emitter( Emitters.tier3CuttingEmitter, Item.pickaxeDiamond, Item.shovelDiamond, Item.pickaxeDiamond, Item.shovelDiamond )
  }

  private def registerLenses() = {
    addModuleLensGrinder( 600, PreciseLens,
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> OpticalGlass ),
      ( 'I' -> Item.ingotIron ) )

    addModuleLensGrinder( 1200, WideLens,
      "IGI",
      "GEG",
      "IGI",
      ( 'G' -> OpticalGlass ),
      ( 'I' -> Item.ingotIron ),
      ( 'E' -> Item.emerald ) )
  }

  private def registerBarrels() = {
    addModuleShaped( BeamBarrel,
      "GI ",
      "IDI",
      " IG",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'D' -> Tier2Diode ) )

    addModuleShaped( BlasterBarrel,
      "GI ",
      "ISI",
      " IG",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'S' -> Shutter ) )
  }

  private def registerMisc() = {
    addShaped( Blocks.gunBench.asStack,
      "II",
      "BB",
      'I' -> Item.ingotIron,
      'B' -> Block.workbench )

    addShaped( Blocks.lensGrinder.asStack,
      "III",
      "SGS",
      "III",
      'I' -> Item.ingotIron,
      'S' -> Block.sand,
      'G' -> Block.glass )

    addSmelting( Block.glass, OpticalGlass.asStack( 3 ), 0.1f )

    addShaped( RadiantDust.asStack,
      "RGR",
      "GRG",
      "RGR",
      'R' -> Item.redstone,
      'G' -> Item.glowstone )

    addShaped( Shutter.asStack,
      "I B",
      "PTR",
      'P' -> Block.pistonBase,
      'T' -> Block.torchRedstoneActive,
      'R' -> Item.redstone,
      'I' -> Item.ingotIron,
      'B' -> Block.stoneButton )
  }

  private def registerCasings() : Unit = {
    def addCasing( casing : Item, heatSink : Item ) : Unit = {
      addShaped( casing.asStack,
        "ISI",
        'I' -> Item.ingotIron,
        'S' -> heatSink )
    }
    addCasing( Tier1ChamberCasing, Tier1HeatSink )
    addCasing( Tier2ChamberCasing, Tier2HeatSink )
    addCasing( Tier3ChamberCasing, Tier3HeatSink )
  }

  private def registerHeatSinks() : Unit = {
    def addHeatSink( heatSink : Item, core : Any ) : Unit = {
      addShaped( heatSink.asStack,
        "ICI",
        "ICI",
        "ICI",
        'I' -> Item.ingotIron,
        'C' -> core )
    }
    addHeatSink( Tier1HeatSink, Item.snowball )
    addHeatSink( Tier2HeatSink, Block.blockSnow )
    addHeatSink( Tier3HeatSink, Block.ice )
  }

  private def registerDiodes() : Unit = {
    def addDiode( time : Short, diode : Item, wire : Any, core : Any ) : Unit = {
      addLensGrinder( time, diode.asStack,
        "GGG",
        "WCW",
        "GGG",
        'W' -> wire,
        'G' -> Block.thinGlass,
        'C' -> core )
    }
    addDiode( 300, Tier1Diode, Item.ingotIron, Block.blockRedstone )
    addDiode( 450, Tier2Diode, Item.ingotIron, Block.glowStone )
    addDiode( 600, Tier3Diode, Item.ingotGold, Item.netherStar )
  }

  private def registerDopedGlass() : Unit = {
    addSmelting( RedstoneDustedGlass, RedstoneDopedGlass.asStack, 0.1f )
    addSmelting( GlowstoneDustedGlass, GlowstoneDopedGlass.asStack, 0.1f )
    addSmelting( RadiantDustedGlass, RadiantDopedGlass.asStack, 0.1f )
  }

  private def registerDustedGlass() : Unit = {
    addShapeless( RedstoneDustedGlass.asStack, Item.redstone, OpticalGlass )
    addShapeless( GlowstoneDustedGlass.asStack, Item.glowstone, OpticalGlass )
    addShapeless( RadiantDustedGlass.asStack, RadiantDust, OpticalGlass )
  }

  private def registerGainMedia(): Unit = {
    def addGainMediumRecipe( medium : Item, ticks : Short, glass : Item ) : Unit = {
        addLensGrinder( ticks, medium.asStack,
          "GGG",
          "MGM",
          "GGG",
          ('M' -> Item.ingotGold ),
          ('G' -> glass ) )
    }
    addGainMediumRecipe( Tier3GainMedium, 1200, RadiantDopedGlass )
    addGainMediumRecipe( Tier2GainMedium, 900, GlowstoneDopedGlass )
    addGainMediumRecipe( Tier1GainMedium, 600, RedstoneDopedGlass )
  }
}