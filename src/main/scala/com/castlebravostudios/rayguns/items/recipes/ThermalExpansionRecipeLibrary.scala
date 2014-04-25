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
import net.minecraft.nbt.NBTTagCompound
import cpw.mods.fml.common.event.FMLInterModComms
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidRegistry

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
    toSend.setCompoundTag( "input", new NBTTagCompound( ) );
    toSend.setCompoundTag( "output", new NBTTagCompound( ) );
    toSend.setCompoundTag( "fluid", new NBTTagCompound( ) );

    input.writeToNBT( toSend.getCompoundTag( "input" ) );
    output.writeToNBT( toSend.getCompoundTag( "output" ) );
    toSend.setBoolean( "reversible", reversible );
    fluid.writeToNBT( toSend.getCompoundTag( "fluid" ) );
    FMLInterModComms.sendMessage( "ThermalExpansion", "TransposerFillRecipe", toSend );
  }

  private def addInductionSmelterRecipe( energyCost : Int, primaryInput : ItemStack, secondaryInput : ItemStack, output : ItemStack ) : Unit = {
    val toSend = new NBTTagCompound();
    toSend.setInteger("energy", energyCost);
    toSend.setCompoundTag("primaryInput", new NBTTagCompound());
    toSend.setCompoundTag("secondaryInput", new NBTTagCompound());
    toSend.setCompoundTag("primaryOutput", new NBTTagCompound());

    primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
    secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
    output.writeToNBT(toSend.getCompoundTag("primaryOutput"));
    FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
  }


  def registerRecipes() : Unit = {
    registerAccessories()
    registerBatteries()
    registerBodies()
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

  def getIngredientItems : Seq[Item] = Seq(
    OpticalGlass,
    RedstoneDopedGlass,
    GlowstoneDopedGlass,
    Tier1Diode,
    Tier2Diode,
    Tier3Diode,
    Tier1HeatSink,
    Tier2HeatSink,
    Tier3HeatSink,
    Tier1ChamberCasing,
    Tier2ChamberCasing,
    Tier3ChamberCasing,
    RadiantDust,
    RadiantDopedGlass,
    Shutter,
    Tier1GainMedium,
    Tier2GainMedium,
    Tier3GainMedium
  )

  private def registerAccessories() = {
    addModuleShaped( HighEfficiencyWiring,
      "CCC",
      "III",
      "CCC",
      ( 'C' -> getTEItem( "dustBlizz" ) ),
      ( 'I' -> getTEItem( "ingotElectrum" ) ) )
    addModuleShaped( RefireCapacitor,
      "LPL",
      "LPL",
      " C ",
      ( 'C' -> getTEItem( "powerCoilElectrum" ) ),
      ( 'L' -> getTEItem( "conduitEnergyBasic" ) ),
      ( 'P' -> Item.paper ) )
    addModuleShaped( SolarPanel,
      "GGG",
      "III",
      "RCR",
      ( 'I' -> Item.ingotIron ),
      ( 'R' -> getTEItem( "conduitEnergyBasic" ) ),
      ( 'G' -> Block.glass ),
      ( 'C' -> getTEItem( "powerCoilSilver" ) ) )
    addModuleShaped( ChargeCapacitor,
      "GLG",
      "GLG",
      "BRB",
      ( 'G' -> Item.ingotGold ),
      ( 'L' -> Block.glass ),
      ( 'B' -> BasicBattery ),
      ( 'R' -> getTEItem( "powerCoilElectrum" ) ) )
  }

  private def registerBatteries() = {
    def addBatteryRecipe( battery : RaygunBattery, core : Any ) : Unit = {
      addModuleShaped( battery,
        "IGI",
        "IRI",
        "IRI",
        ( 'G' -> getTEItem( "powerCoilElectrum" ) ),
        ( 'I' -> Item.ingotIron ),
        ( 'R' -> core ) )
    }

    addBatteryRecipe( BasicBattery, getTEItem( "capacitorBasic" ) )
    addBatteryRecipe( AdvancedBattery, getTEItem( "capacitorHardened" ) )
    addBatteryRecipe( UltimateBattery, getTEItem( "capacitorReinforced" ) )
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
      "SSS",
      "FMF",
      "CRC",
      ( 'S' -> Block.sand ),
      ( 'F' -> Item.flint ),
      ( 'M' -> getTEItem( "machineFrame" ) ),
      ( 'C' -> getTEItem( "ingotCopper" ) ),
      ( 'R' -> getTEItem( "powerCoilGold" ) ) )

    addInductionSmelterRecipe(800, getTEItem( "dustLead" ),
        Block.glass.asStack, OpticalGlass.asStack( 3 ) )

    addFluidTransposerRecipe( 800, Item.redstone.asStack,
      RadiantDust.asStack, getFluidStack( "glowstone", 500 ), false );

    addShaped( Shutter.asStack,
      "PT",
      "I ",
      'P' -> getTEItem( "pneumaticServo" ),
      'T' -> getTEItem( "gearTin" ),
      'I' -> Item.ingotIron )
  }

  private def registerCasings() : Unit = {
    def addCasing( casing : Item, metal : Any, heatSink : Item ) : Unit = {
      addShaped( casing.asStack,
        "MSM",
        'M' -> metal,
        'S' -> heatSink )
    }
    addCasing( Tier1ChamberCasing, getTEItem( "ingotTin" ), Tier1HeatSink )
    addCasing( Tier2ChamberCasing, getTEItem( "ingotInvar" ), Tier2HeatSink )
    addCasing( Tier3ChamberCasing, getTEItem( "glassHardened" ), Tier3HeatSink )
  }

  private def registerHeatSinks() : Unit = {
    def addHeatSink( heatSink : Item, core : Any ) : Unit = {
      addShaped( heatSink.asStack,
        "ICI",
        "ICI",
        "ICI",
        'I' -> getTEItem( "ingotInvar" ),
        'C' -> core )
    }
    addHeatSink( Tier1HeatSink, Item.bucketWater )
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
        'G' -> Block.thinGlass,
        'C' -> core )
    }
    addDiode( 300, Tier1Diode, getTEItem( "ingotElectrum" ), Block.blockRedstone )
    addDiode( 450, Tier2Diode, getTEItem( "ingotElectrum" ), Block.glowStone )
    addDiode( 600, Tier3Diode, getTEItem( "ingotElectrum" ), Item.netherStar )
  }

  private def registerDopedGlass() : Unit = {
    addInductionSmelterRecipe(800, Item.redstone.asStack, OpticalGlass.asStack, RedstoneDopedGlass.asStack )
    addInductionSmelterRecipe(800, Item.glowstone.asStack, OpticalGlass.asStack, GlowstoneDopedGlass.asStack )
    addInductionSmelterRecipe(800, RadiantDust.asStack, OpticalGlass.asStack, RadiantDopedGlass.asStack )
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