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

object Items {

  def registerItems : Unit = {
    registerItem( RayGun )
    registerItem( BrokenGun )
    registerItem( EnergizedDiamond )
    registerItem( GlassGainMedium )
    registerItem( GlowstoneGainMedium )
    registerItem( DiamondGainMedium )

    registerItem( BasicBattery )
    registerItem( AdvancedBattery )
    registerItem( UltimateBattery )
    registerItem( InfiniteBattery )

    registerItem( PreciseLens )
    registerItem( WideLens )
    registerItem( PreciseBeamLens )
    registerItem( ChargeLens )
    registerItem( ChargeBeamLens )

    registerItem( ExtendedBattery )
    registerItem( RefireCapacitor )
    registerItem( SolarPanel )

    registerItem( MantisBody )
    registerItem( FireflyBody )

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

    registerItem( LaserChamber )
    registerItem( HeatRayChamber )
    registerItem( LifeForceChamber )
    registerItem( FrostRayChamber )
    registerItem( FortifiedSunlightChamber )
    registerItem( ExplosiveChamber )
    registerItem( DeathRayChamber )
    registerItem( EnderChamber )
    registerItem( LightningChamber )
    registerItem( ImpulseChamber )
    registerItem( TractorChamber )
    registerItem( Tier1CuttingChamber )
    registerItem( Tier2CuttingChamber )
    registerItem( Tier3CuttingChamber )
  }

  /**
   * Since items are Objects, and therefore instantiated when first loaded,
   * I don't actually have to register them anywhere. This merely forces the
   * VM to load (and thus register for me) the Objects.
   */
  private def registerItem( item : Item ) : Unit = {
    item.hashCode()
  }
}