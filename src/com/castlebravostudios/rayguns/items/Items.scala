package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.items.accessories._
import com.castlebravostudios.rayguns.items.batteries._
import com.castlebravostudios.rayguns.items.bodies._
import com.castlebravostudios.rayguns.items.chambers._
import com.castlebravostudios.rayguns.items.emitters._
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.items.misc._
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.api.ModuleRegistrationHelper
import com.castlebravostudios.rayguns.mod.Config

object Items {

  import ModuleRegistrationHelper._

  def registerItems : Unit = {
    registerItem( RayGun )
    registerItem( BrokenGun )
    registerItem( EnergizedDiamond )
    registerItem( GlassGainMedium )
    registerItem( GlowstoneGainMedium )
    registerItem( DiamondGainMedium )

    registerItem( Emitters.shrinkRayEmitter )
    registerItem( Emitters.laserEmitter )
    registerItem( Emitters.heatRayEmitter )
    registerItem( Emitters.lifeForceEmitter )
    registerItem( Emitters.frostRayEmitter )
    registerItem( Emitters.fortifiedSunlightEmitter )
    registerItem( Emitters.explosiveEmitter )
    registerItem( Emitters.deathRayEmitter )
    registerItem( Emitters.enderEmitter )
    registerItem( Emitters.lightningEmitter )
    registerItem( Emitters.impulseEmitter )
    registerItem( Emitters.tractorEmitter )
    registerItem( Emitters.tier1CuttingEmitter )
    registerItem( Emitters.tier2CuttingEmitter )
    registerItem( Emitters.tier3CuttingEmitter )

    registerModule( BasicBattery, Config.basicBattery )
    registerModule( AdvancedBattery, Config.advancedBattery )
    registerModule( UltimateBattery, Config.ultimateBattery )
    registerModule( InfiniteBattery, Config.infiniteBattery )

    registerModule( PreciseLens, Config.preciseLens )
    registerModule( WideLens, Config.wideLens )
    registerModule( PreciseBeamLens, Config.preciseBeamLens )
    registerModule( ChargeLens, Config.chargeLens )
    registerModule( ChargeBeamLens, Config.chargeBeamLens )

    registerModule( ExtendedBattery, Config.extendedBattery )
    registerModule( RefireCapacitor, Config.refireCapacitor )
    registerModule( SolarPanel, Config.solarPanel )

    registerModule( MantisBody, Config.mantisBody )
    registerModule( FireflyBody, Config.fireflyBody )

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
  }

  /**
   * Since items are Objects, and therefore instantiated when first loaded,
   * I don't actually have to register them anywhere. This merely forces the
   * VM to load (and thus register for me) the Objects.
   */
  private def registerItem( item : Any) : Unit = {
    item.hashCode()
  }
}