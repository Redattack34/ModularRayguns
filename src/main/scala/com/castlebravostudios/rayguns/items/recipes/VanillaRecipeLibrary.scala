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

import com.castlebravostudios.rayguns.api.items._
import com.castlebravostudios.rayguns.items.RaygunsBlocks
import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.barrels._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.frames._
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import com.castlebravostudios.rayguns.utils.Extensions.BlockExtensions
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item

//scalastyle:on

object VanillaRecipeLibrary extends RecipeLibrary {

  def registerRecipes() : Unit = {
    registerAccessories()
    registerBatteries()
    registerFrames()
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

  def getIngredientItems : Seq[(Item, String)] = Seq(
    ( OpticalGlass, "opticalGlass" ),
    ( RedstoneDopedGlass, "redstoneGlass" ),
    ( GlowstoneDopedGlass, "glowstoneGlass" ),
    ( Tier1Diode, "tier1Diode" ),
    ( Tier2Diode, "tier2Diode" ),
    ( Tier3Diode, "tier3Diode" ),
    ( Tier1HeatSink, "tier1HeatSink" ),
    ( Tier2HeatSink, "tier2HeatSink" ),
    ( Tier3HeatSink, "tier3HeatSink" ),
    ( Tier1ChamberCasing, "tier1Casing" ),
    ( Tier2ChamberCasing, "tier2Casing" ),
    ( Tier3ChamberCasing, "Tier3Casing" ),
    ( RadiantDust, "radiantDust" ),
    ( RadiantDopedGlass, "radiantGlass" ),
    ( Shutter, "shutter" ),
    ( Tier1GainMedium, "tier1GainMedium" ),
    ( Tier2GainMedium, "tier2GainMedium" ),
    ( Tier3GainMedium, "tier3GainMedium" ),
    ( RedstoneDustedGlass, "redstoneDustedGlass" ),
    ( GlowstoneDustedGlass, "glowstoneDustedGlass" ),
    ( RadiantDustedGlass, "radiantDustedGlass" )
  )

  private def registerAccessories() = {
    addModuleShapedOre( HighEfficiencyWiring,
      "CCC",
      "III",
      "CCC",
      ( 'C' -> Blocks.ice ),
      ( 'I' -> "ingotIron" ) )
    addModuleShapedOre( RefireCapacitor,
      "IPI",
      "IPI",
      " S ",
      ( 'S' -> Shutter ),
      ( 'I' -> "ingotIron" ),
      ( 'P' -> Items.paper ) )
    addModuleShapedOre( SolarPanel,
      "GGG",
      "III",
      "RRR",
      ( 'I' -> "ingotIron" ),
      ( 'R' -> "blockRedstone" ),
      ( 'G' -> "blockGlassColorless" ) )
    addModuleShapedOre( ChargeCapacitor,
      "GLG",
      "GLG",
      "B B",
      ( 'G' -> "ingotGold" ),
      ( 'L' -> "blockGlassColorless" ),
      ( 'B' -> BasicBattery ) )
  }

  private def registerBatteries() = {
    def addBatteryRecipe( battery : RaygunBattery, core : Any ) : Unit = {
      addModuleShapedOre( battery,
        "IGI",
        "IRI",
        "IRI",
        ( 'G' -> "ingotGold" ),
        ( 'I' -> "ingotIron" ),
        ( 'R' -> core ) )
    }

    addBatteryRecipe( BasicBattery, "blockRedstone" )
    addBatteryRecipe( AdvancedBattery, BasicBattery )
    addBatteryRecipe( UltimateBattery, AdvancedBattery )
  }

  private def registerFrames() = {
    def addFrameRecipe( frame : RaygunFrame, core : Any ) : Unit = {
      addModuleShapedOre( frame,
        "IR ",
        " IR",
        " LI",
        ( 'L' -> Blocks.lever ),
        ( 'R' -> core ),
        ( 'I' -> "ingotIron" ) )
    }
    addFrameRecipe( FireflyFrame, "dyeRed" )
    addFrameRecipe( MantisFrame, "dyeGreen" )
  }

  private def registerChambers() = {
    def registerChamber( chamber : RaygunChamber, emitter : Item, medium : Item, diode : Item, casing : Item ) : Unit = {
      addModuleShapedOre( chamber,
        "CDC",
        "MME",
        "CDC",
        ( 'D' -> diode ),
        ( 'C' -> casing ),
        ( 'M' -> medium ),
        ( 'E' -> emitter ) )
    }
    def registerT1Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier1GainMedium, Tier1Diode, Tier1ChamberCasing )
    def registerT2Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier2GainMedium, Tier2Diode, Tier2ChamberCasing )
    def registerT3Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, Tier3GainMedium, Tier3Diode, Tier3ChamberCasing )

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
      addShapedOre( emitter.asStack,
        "ITI",
        "LDR",
        "IBI",
        'I' -> "ingotIron",
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

    registerT1Emitter( Emitters.laserEmitter, "dustRedstone", "dustRedstone", "dustRedstone", "dustRedstone" )
    registerT1Emitter( Emitters.heatRayEmitter, Items.coal, Items.lava_bucket, Items.coal, Items.lava_bucket )
    registerT1Emitter( Emitters.lightningEmitter, "blockIron", "blockRedstone", "blockIron", "blockRedstone" )
    registerT1Emitter( Emitters.tier1CuttingEmitter, Items.stone_pickaxe, Items.stone_shovel, Items.stone_pickaxe, Items.stone_shovel )

    registerT2Emitter( Emitters.frostRayEmitter, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.snow )
    registerT2Emitter( Emitters.lifeForceEmitter, Items.speckled_melon, Items.ghast_tear, Items.speckled_melon, Items.ghast_tear )
    registerT2Emitter( Emitters.fortifiedSunlightEmitter, "logWood", "logWood", "logWood", "logWood" )
    registerT2Emitter( Emitters.enderEmitter, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl )
    registerT2Emitter( Emitters.impulseEmitter, Blocks.piston, Blocks.piston, Blocks.piston, Blocks.piston )
    registerT2Emitter( Emitters.tractorEmitter, Blocks.sticky_piston, Blocks.sticky_piston, Blocks.sticky_piston, Blocks.sticky_piston )
    registerT2Emitter( Emitters.matterTransporterEmitter, Items.ender_pearl, Blocks.piston, Items.ender_pearl, Blocks.piston )
    registerT2Emitter( Emitters.tier2CuttingEmitter, Items.iron_pickaxe, Items.iron_shovel, Items.iron_pickaxe, Items.iron_shovel )

    val witherSkull = Items.skull.asStack( 1, 1 )
    registerT3Emitter( Emitters.deathRayEmitter, witherSkull, witherSkull, witherSkull, witherSkull )
    registerT3Emitter( Emitters.explosiveEmitter, Blocks.tnt, Blocks.tnt, Blocks.tnt, Blocks.tnt )
    registerT3Emitter( Emitters.tier3CuttingEmitter, Items.diamond_pickaxe, Items.diamond_shovel, Items.diamond_pickaxe, Items.diamond_shovel )
  }

  private def registerLenses() = {
    addModuleLensGrinder( 600, PreciseLens,
      "IGI",
      "GGG",
      "IGI",
      ( 'G' -> OpticalGlass ),
      ( 'I' -> "ingotIron" ) )

    addModuleLensGrinder( 1200, WideLens,
      "IGI",
      "GEG",
      "IGI",
      ( 'G' -> OpticalGlass ),
      ( 'I' -> "ingotIron" ),
      ( 'E' -> "gemEmerald" ) )
  }

  private def registerBarrels() = {
    addModuleShapedOre( BeamBarrel,
      "GI ",
      "IDI",
      " IG",
      ( 'G' -> "blockGlassColorless" ),
      ( 'I' -> "ingotIron" ),
      ( 'D' -> Tier2Diode ) )

    addModuleShapedOre( BlasterBarrel,
      "GI ",
      "ISI",
      " IG",
      ( 'G' -> "blockGlassColorless" ),
      ( 'I' -> "ingotIron" ),
      ( 'S' -> Shutter ) )
  }

  private def registerMisc() = {
    addShapedOre( RaygunsBlocks.gunBench.asStack,
      "II",
      "BB",
      'I' -> "ingotIron",
      'B' -> Blocks.crafting_table )

    addShapedOre( RaygunsBlocks.lensGrinder.asStack,
      "III",
      "SGS",
      "III",
      'I' -> "ingotIron",
      'S' -> Blocks.sand,
      'G' -> "blockGlassColorless" )

    addSmelting( Blocks.glass, OpticalGlass.asStack( 3 ), 0.1f )

    addShapedOre( RadiantDust.asStack,
      "RGR",
      "GRG",
      "RGR",
      'R' -> "dustRedstone",
      'G' -> Items.glowstone_dust )

    addShapedOre( Shutter.asStack,
      "I B",
      "PTR",
      'P' -> Blocks.piston,
      'T' -> Blocks.redstone_torch,
      'R' -> "dustRedstone",
      'I' -> "ingotIron",
      'B' -> Blocks.stone_button )
  }

  private def registerCasings() : Unit = {
    def addCasing( casing : Item, metal : Any, heatSink : Item ) : Unit = {
      addShapedOre( casing.asStack,
        "MSM",
        'M' -> metal,
        'S' -> heatSink )
    }
    addCasing( Tier1ChamberCasing, "ingotIron", Tier1HeatSink )
    addCasing( Tier2ChamberCasing, "ingotGold", Tier2HeatSink )
    addCasing( Tier3ChamberCasing, Items.diamond, Tier3HeatSink )
  }

  private def registerHeatSinks() : Unit = {
    def addHeatSink( heatSink : Item, core : Any ) : Unit = {
      addShapedOre( heatSink.asStack,
        "ICI",
        "ICI",
        "ICI",
        'I' -> "ingotIron",
        'C' -> core )
    }
    addHeatSink( Tier1HeatSink, Items.snowball )
    addHeatSink( Tier2HeatSink, Blocks.snow )
    addHeatSink( Tier3HeatSink, Blocks.ice )
  }

  private def registerDiodes() : Unit = {
    def addDiode( time : Short, diode : Item, wire : Any, core : Any ) : Unit = {
      addLensGrinder( time, diode.asStack,
        "GGG",
        "WCW",
        "GGG",
        'W' -> wire,
        'G' -> Blocks.glass_pane,
        'C' -> core )
    }
    addDiode( 300, Tier1Diode, "ingotIron", "blockRedstone" )
    addDiode( 450, Tier2Diode, "ingotIron", Blocks.glowstone )
    addDiode( 600, Tier3Diode, "ingotGold", Items.nether_star )
  }

  private def registerDopedGlass() : Unit = {
    addSmelting( RedstoneDustedGlass, RedstoneDopedGlass.asStack, 0.1f )
    addSmelting( GlowstoneDustedGlass, GlowstoneDopedGlass.asStack, 0.1f )
    addSmelting( RadiantDustedGlass, RadiantDopedGlass.asStack, 0.1f )
  }

  private def registerDustedGlass() : Unit = {
    addShapelessOre( RedstoneDustedGlass.asStack, "dustRedstone", OpticalGlass )
    addShapelessOre( GlowstoneDustedGlass.asStack, Items.glowstone_dust, OpticalGlass )
    addShapeless( RadiantDustedGlass.asStack, RadiantDust, OpticalGlass )
  }

  private def registerGainMedia(): Unit = {
    def addGainMediumRecipe( medium : Item, ticks : Short, glass : Item ) : Unit = {
        addLensGrinder( ticks, medium.asStack,
          "GGG",
          "MGM",
          "GGG",
          ('M' -> "ingotGold" ),
          ('G' -> glass ) )
    }
    addGainMediumRecipe( Tier3GainMedium, 1200, RadiantDopedGlass )
    addGainMediumRecipe( Tier2GainMedium, 900, GlowstoneDopedGlass )
    addGainMediumRecipe( Tier1GainMedium, 600, RedstoneDopedGlass )
  }
}