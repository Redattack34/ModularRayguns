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
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack

import cpw.mods.fml.common.event.FMLInterModComms
import cpw.mods.fml.common.registry.GameRegistry

//scalastyle:on

object ThermalExpansionRecipeLibrary extends RecipeLibrary {

  private def getTEItem( item : String, count : Int = 1 ) : ItemStack =
    GameRegistry.findItemStack("ThermalExpansion", item, count)

  private def getFluidStack( fluid : String, amount : Int ) : FluidStack =
    FluidRegistry.getFluidStack( fluid, amount )

  private def addFluidTransposerRecipe( energyCost : Int, input : ItemStack,
      output : ItemStack, fluid : FluidStack, reversible : Boolean = false ) : Unit = {
    val toSend = new NBTTagCompound();
    toSend.setInteger( "energy", energyCost );
    toSend.setTag( "input", new NBTTagCompound( ) );
    toSend.setTag( "output", new NBTTagCompound( ) );
    toSend.setTag( "fluid", new NBTTagCompound( ) );

    input.writeToNBT( toSend.getCompoundTag( "input" ) );
    output.writeToNBT( toSend.getCompoundTag( "output" ) );
    toSend.setBoolean( "reversible", reversible );
    fluid.writeToNBT( toSend.getCompoundTag( "fluid" ) );
    FMLInterModComms.sendMessage( "ThermalExpansion", "TransposerFillRecipe", toSend );
  }

  private def addInductionSmelterRecipe( energyCost : Int, primaryInput : ItemStack, secondaryInput : ItemStack, output : ItemStack ) : Unit = {
    val toSend = new NBTTagCompound();
    toSend.setInteger("energy", energyCost);
    toSend.setTag("primaryInput", new NBTTagCompound());
    toSend.setTag("secondaryInput", new NBTTagCompound());
    toSend.setTag("primaryOutput", new NBTTagCompound());

    primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
    secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
    output.writeToNBT(toSend.getCompoundTag("primaryOutput"));
    FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
  }


  def registerRecipes() : Unit = {
    registerAccessories()
    registerBatteries()
    registerFrames()
    registerChambers()
    registerEmitters()
    registerLenses()
    registerBarrels()
    registerGainMedia()
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
    ( Tier3GainMedium, "tier3GainMedium" )
  )

  private def registerAccessories() = {
    addModuleShapedOre( HighEfficiencyWiring,
      "CCC",
      "III",
      "CCC",
      ( 'C' -> getTEItem( "dustBlizz" ) ),
      ( 'I' -> "ingotElectrum" ) )
    addModuleShapedOre( RefireCapacitor,
      "LPL",
      "LPL",
      " C ",
      ( 'C' -> getTEItem( "powerCoilElectrum" ) ),
      ( 'L' -> getTEItem( "conduitEnergyBasic" ) ),
      ( 'P' -> Items.paper ) )
    addModuleShapedOre( SolarPanel,
      "GGG",
      "III",
      "RCR",
      ( 'I' -> "ingotIron" ),
      ( 'R' -> getTEItem( "conduitEnergyBasic" ) ),
      ( 'G' -> "blockGlass" ),
      ( 'C' -> getTEItem( "powerCoilSilver" ) ) )
    addModuleShapedOre( ChargeCapacitor,
      "GLG",
      "GLG",
      "BRB",
      ( 'G' -> "ingotGold" ),
      ( 'L' -> "blockGlass" ),
      ( 'B' -> BasicBattery ),
      ( 'R' -> getTEItem( "powerCoilElectrum" ) ) )
  }

  private def registerBatteries() = {
    def addBatteryRecipe( battery : RaygunBattery, core : Any ) : Unit = {
      addModuleShapedOre( battery,
        "IGI",
        "IRI",
        "IRI",
        ( 'G' -> getTEItem( "powerCoilElectrum" ) ),
        ( 'I' -> "ingotIron" ),
        ( 'R' -> core ) )
    }

    addBatteryRecipe( BasicBattery, getTEItem( "capacitorBasic" ) )
    addBatteryRecipe( AdvancedBattery, getTEItem( "capacitorHardened" ) )
    addBatteryRecipe( UltimateBattery, getTEItem( "capacitorReinforced" ) )
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
    registerT2Emitter( Emitters.fortifiedSunlightEmitter, "woodLog", "woodLog", "woodLog", "woodLog" )
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
      ( 'G' -> "blockGlass" ),
      ( 'I' -> "ingotIron" ),
      ( 'D' -> Tier2Diode ) )

    addModuleShapedOre( BlasterBarrel,
      "GI ",
      "ISI",
      " IG",
      ( 'G' -> "blockGlass" ),
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
      "SSS",
      "FMF",
      "CRC",
      ( 'S' -> Blocks.sand ),
      ( 'F' -> Items.flint ),
      ( 'M' -> getTEItem( "machineFrame" ) ),
      ( 'C' -> "ingotCopper" ),
      ( 'R' -> getTEItem( "powerCoilGold" ) ) )

    addInductionSmelterRecipe(800, getTEItem( "dustLead" ),
        Blocks.glass.asStack, OpticalGlass.asStack( 3 ) )

    addFluidTransposerRecipe( 800, Items.redstone.asStack,
      RadiantDust.asStack, getFluidStack( "glowstone", 500 ), false );

    addShapedOre( Shutter.asStack,
      "PT",
      "I ",
      'P' -> getTEItem( "pneumaticServo" ),
      'T' -> getTEItem( "gearTin" ),
      'I' -> "ingotIron" )
  }

  private def registerCasings() : Unit = {
    def addCasing( casing : Item, metal : Any, heatSink : Item ) : Unit = {
      addShapedOre( casing.asStack,
        "MSM",
        'M' -> metal,
        'S' -> heatSink )
    }
    addCasing( Tier1ChamberCasing, "ingotTin", Tier1HeatSink )
    addCasing( Tier2ChamberCasing, "ingotInvar", Tier2HeatSink )
    addCasing( Tier3ChamberCasing, getTEItem( "glassHardened" ), Tier3HeatSink )
  }

  private def registerHeatSinks() : Unit = {
    def addHeatSink( heatSink : Item, core : Any ) : Unit = {
      addShapedOre( heatSink.asStack,
        "ICI",
        "ICI",
        "ICI",
        'I' -> "ingotInvar",
        'C' -> core )
    }
    addHeatSink( Tier1HeatSink, Items.water_bucket )
    addHeatSink( Tier2HeatSink, getTEItem( "dustBlizz" ) )
    addHeatSink( Tier3HeatSink, getTEItem( "bucketCryotheum" ) )
  }

  private def registerDiodes() : Unit = {
    def addDiode( time : Short, diode : Item, wire : Any, core : Any ) : Unit = {
      addLensGrinder( time, diode.asStack,
        "GGG",
        "WCW",
        "GGG",
        'W' -> wire,
        'G' -> "paneGlassColorless",
        'C' -> core )
    }
    addDiode( 300, Tier1Diode, "ingotElectrum", "blockRedstone" )
    addDiode( 450, Tier2Diode, "ingotElectrum", Blocks.glowstone )
    addDiode( 600, Tier3Diode, "ingotElectrum", Items.nether_star )
  }

  private def registerDopedGlass() : Unit = {
    addInductionSmelterRecipe(800, Items.redstone.asStack, OpticalGlass.asStack, RedstoneDopedGlass.asStack )
    addInductionSmelterRecipe(800, Items.glowstone_dust.asStack, OpticalGlass.asStack, GlowstoneDopedGlass.asStack )
    addInductionSmelterRecipe(800, RadiantDust.asStack, OpticalGlass.asStack, RadiantDopedGlass.asStack )
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