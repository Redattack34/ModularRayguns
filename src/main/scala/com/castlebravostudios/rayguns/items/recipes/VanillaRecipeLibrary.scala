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
    registerMisc()
  }

  private def registerAccessories() = {
    addModuleRecipe( ExtendedBattery,
      "SI ",
      "IRI",
      " I ",
      ( 'S' -> Emitters.shrinkRayEmitter ),
      ( 'I' -> Item.ingotIron ),
      ( 'R' -> Block.blockRedstone ) )
    addModuleRecipe( RefireCapacitor,
      "IPI",
      "IPI",
      " S ",
      ( 'S' -> Shutter ),
      ( 'I' -> Item.ingotIron ),
      ( 'P' -> Item.paper ) )
    addModuleRecipe( SolarPanel,
      "GGG",
      "III",
      "RRR",
      ( 'I' -> Item.ingotIron ),
      ( 'R' -> Block.blockRedstone ),
      ( 'G' -> Block.glass ) )
    addModuleRecipe( ChargeCapacitor,
      "GLG",
      "GLG",
      "B B",
      ( 'G' -> Item.ingotGold ),
      ( 'L' -> Block.glass ),
      ( 'B' -> BasicBattery ) )
  }

  private def registerBatteries() = {
    def addBatteryRecipe( battery : RaygunBattery, core : Any ) : Unit = {
      addModuleRecipe( battery,
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
      addModuleRecipe( body,
        "IR ",
        " IR",
        " LI",
        ( 'L' -> Block.lever ),
        ( 'R' -> core ),
        ( 'I' -> Item.ingotIron ) )
    }
    addBodyRecipe( FireflyBody, new ItemStack( Item.dyePowder, 1, 1 ) )
    addBodyRecipe( MantisBody, new ItemStack( Item.dyePowder, 1, 2 ) )
  }

  private def registerChambers() = {
    def registerChamber( chamber : RaygunChamber, emitter : Item, medium : Item, diode : Item, casing : Item ) : Unit = {
      addModuleRecipe( chamber,
        "CDC",
        "MME",
        "CDC",
        ( 'D' -> diode ),
        ( 'C' -> casing ),
        ( 'M' -> medium ),
        ( 'E' -> emitter ) )
    }
    def registerT1Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, GlassGainMedium, Tier1Diode, Item.ingotIron )
    def registerT2Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, GlowstoneGainMedium, Tier2Diode, Item.ingotGold )
    def registerT3Chamber( chamber : RaygunChamber, emitter : Item ) : Unit =
      registerChamber( chamber, emitter, DiamondGainMedium, Tier3Diode, Item.diamond )

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
      GameRegistry.addRecipe( new ItemStack( emitter, 1 ),
        "ITI",
        "LDR",
        "IBI",
        'I' : Character, Item.ingotIron,
        'D' : Character, core,
        'T' : Character, top,
        'R' : Character, right,
        'B' : Character, bottom,
        'L' : Character, left )
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
    registerT1Emitter( Emitters.shrinkRayEmitter, Block.pistonBase, Block.pistonBase, Block.pistonBase, Block.pistonBase )
    registerT1Emitter( Emitters.tier1CuttingEmitter, Item.pickaxeStone, Item.shovelStone, Item.pickaxeStone, Item.shovelStone )

    registerT2Emitter( Emitters.frostRayEmitter, Block.ice, Block.blockSnow, Block.ice, Block.blockSnow )
    registerT2Emitter( Emitters.lifeForceEmitter, Item.speckledMelon, Item.ghastTear, Item.speckledMelon, Item.ghastTear )
    registerT2Emitter( Emitters.fortifiedSunlightEmitter, Block.wood, Block.wood, Block.wood, Block.wood )
    registerT2Emitter( Emitters.enderEmitter, Item.enderPearl, Item.enderPearl, Item.enderPearl, Item.enderPearl )
    registerT2Emitter( Emitters.impulseEmitter, Block.pistonBase, Block.pistonBase, Block.pistonBase, Block.pistonBase )
    registerT2Emitter( Emitters.tractorEmitter, Block.pistonStickyBase, Block.pistonStickyBase, Block.pistonStickyBase, Block.pistonStickyBase )
    registerT2Emitter( Emitters.matterTransporterEmitter, Item.enderPearl, Block.pistonBase, Item.enderPearl, Block.pistonBase )
    registerT2Emitter( Emitters.tier2CuttingEmitter, Item.pickaxeIron, Item.shovelIron, Item.pickaxeIron, Item.shovelIron )

    val witherSkull = new ItemStack( Item.skull, 1, 1 )
    registerT3Emitter( Emitters.deathRayEmitter, witherSkull, witherSkull, witherSkull, witherSkull )
    registerT3Emitter( Emitters.explosiveEmitter, Block.tnt, Block.tnt, Block.tnt, Block.tnt )
    registerT3Emitter( Emitters.tier3CuttingEmitter, Item.pickaxeDiamond, Item.shovelDiamond, Item.pickaxeDiamond, Item.shovelDiamond )
  }

  private def registerLenses() = {
    for {
      item <- PreciseLens.item
    } {
      LensGrinderRecipeRegistry.register( 600, new ItemStack(item ),
        "IGI",
        "GGG",
        "IGI",
        ( 'G' -> OpticalGlass ),
        ( 'I' -> Item.ingotIron ) )
    }

    for {
      item <- WideLens.item
    } {
      LensGrinderRecipeRegistry.register( 1200, new ItemStack( item ),
        "IGI",
        "GEG",
        "IGI",
        ( 'G' -> OpticalGlass ),
        ( 'I' -> Item.ingotIron ),
        ( 'D' -> Item.emerald ) )
    }
  }

  private def registerBarrels() = {
    addModuleRecipe( BeamBarrel,
      "GI ",
      "IDI",
      " IG",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'D' -> Tier2Diode ) )

    addModuleRecipe( BlasterBarrel,
      "GI ",
      "ISI",
      " IG",
      ( 'G' -> Block.glass ),
      ( 'I' -> Item.ingotIron ),
      ( 'S' -> Shutter ) )
  }

  private def registerMisc() = {
    def addGainMediumRecipe( medium : Item, ticks : Short, material : AnyRef ) : Unit = {
        LensGrinderRecipeRegistry.register( ticks, new ItemStack( medium, 1 ),
          " M ",
          "MMM",
          " M ",
          ('M' -> material ) )
    }
    addGainMediumRecipe( DiamondGainMedium, 1200, Item.diamond )
    addGainMediumRecipe( GlassGainMedium, 600, Block.glass )
    addGainMediumRecipe( GlowstoneGainMedium, 600, Block.glowStone )

    GameRegistry.addRecipe( new ItemStack( EnergizedDiamond, 1 ),
      "GRG",
      "RDR",
      "GRG",
      'G' : Character, Block.glowStone,
      'R' : Character, Block.blockRedstone,
      'D' : Character, Item.diamond )

    GameRegistry.addRecipe( new ItemStack( Blocks.gunBench, 1 ),
      "II",
      "BB",
      'I' : Character, Item.ingotIron,
      'B' : Character, Block.workbench )

    GameRegistry.addRecipe( new ItemStack( Blocks.lensGrinder, 1 ),
      "III",
      "SGS",
      "III",
      'I' : Character, Item.ingotIron,
      'S' : Character, Block.sand,
      'G' : Character, Block.glass )

    GameRegistry.addSmelting(Block.glass.blockID, new ItemStack( OpticalGlass, 3 ), 0.1f )

    GameRegistry.addShapelessRecipe( new ItemStack( RedstoneDustedGlass ),
        Item.redstone, OpticalGlass )
    GameRegistry.addShapelessRecipe( new ItemStack( GlowstoneDustedGlass ),
        Item.glowstone, OpticalGlass )
    GameRegistry.addShapelessRecipe( new ItemStack( RadiantDustedGlass ),
        RadiantDust, OpticalGlass )

    GameRegistry.addSmelting( RedstoneDustedGlass.itemID,
        new ItemStack( RedstoneDopedGlass ), 0.1f )
    GameRegistry.addSmelting( GlowstoneDustedGlass.itemID,
        new ItemStack( GlowstoneDopedGlass ), 0.1f )
    GameRegistry.addSmelting( RadiantDustedGlass.itemID,
        new ItemStack( RadiantDopedGlass ), 0.1f )

    LensGrinderRecipeRegistry.register( 300, new ItemStack( Tier1Diode ),
        "GGG",
        "IRI",
        "GGG",
        'I' -> Item.ingotIron,
        'G' -> Block.thinGlass,
        'R' -> Block.blockRedstone )

    LensGrinderRecipeRegistry.register( 450, new ItemStack( Tier2Diode ),
        "GGG",
        "ILI",
        "GGG",
        'I' -> Item.ingotIron,
        'G' -> Block.thinGlass,
        'L' -> Block.glowStone )

    LensGrinderRecipeRegistry.register( 600, new ItemStack( Tier3Diode ),
        "GGG",
        "DSD",
        "GGG",
        'D' -> Item.ingotGold,
        'G' -> Block.thinGlass,
        'S' -> Item.netherStar )

    GameRegistry.addRecipe( new ItemStack( Tier1HeatSink ),
      "ISI",
      "ISI",
      "ISI",
      'I' : Character, Item.ingotIron,
      'S' : Character, Item.snowball )

    GameRegistry.addRecipe( new ItemStack( Tier2HeatSink ),
      "ISI",
      "ISI",
      "ISI",
      'I' : Character, Item.ingotIron,
      'S' : Character, Block.blockSnow )

    GameRegistry.addRecipe( new ItemStack( Tier3HeatSink ),
      "ISI",
      "ISI",
      "ISI",
      'I' : Character, Item.ingotIron,
      'S' : Character, Block.ice )

    GameRegistry.addRecipe( new ItemStack( Tier1ChamberCasing ),
      "ISI",
      'I' : Character, Item.ingotIron,
      'S' : Character, Tier1HeatSink )

    GameRegistry.addRecipe( new ItemStack( Tier2ChamberCasing ),
      "ISI",
      'I' : Character, Item.ingotGold,
      'S' : Character, Tier2HeatSink )

    GameRegistry.addRecipe( new ItemStack( Tier3ChamberCasing ),
      "ISI",
      'I' : Character, Item.diamond,
      'S' : Character, Tier3HeatSink )

    GameRegistry.addRecipe( new ItemStack( RadiantDust ),
      "RGR",
      "GRG",
      "RGR",
      'R' : Character, Item.redstone,
      'G' : Character, Item.glowstone )

    GameRegistry.addRecipe( new ItemStack( Shutter ),
      "I B",
      "PTR",
      'P' : Character, Block.pistonBase,
      'T' : Character, Block.torchRedstoneActive,
      'R' : Character, Item.redstone,
      'I' : Character, Item.ingotIron,
      'B' : Character, Block.stoneButton )
  }

  private def addModuleRecipe( module : RaygunModule, params : Any* ) : Unit = {
    val modules = module +: params.flatMap{
      case mod : RaygunModule => Some( mod )
      case (c, mod : RaygunModule) => Some( mod )
      case _ => None
    }

    //Skip modules where the module or a recipe ingredient has been disabled.
    if ( modules.exists( _.item.isEmpty ) ) {
      return
    }

    GameRegistry.addRecipe( ScalaShapedRecipeFactory(
        new ItemStack( module.item.get, 1 ), params:_* ) );
  }
}