package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.items.accessories.ExtendedBattery
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor
import com.castlebravostudios.rayguns.items.accessories.SolarPanel
import com.castlebravostudios.rayguns.items.batteries.AdvancedBattery
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.items.batteries.InfiniteBattery
import com.castlebravostudios.rayguns.items.batteries.UltimateBattery
import com.castlebravostudios.rayguns.items.bodies.FireflyBody
import com.castlebravostudios.rayguns.items.bodies.MantisBody
import com.castlebravostudios.rayguns.items.chambers.FrostRayChamber
import com.castlebravostudios.rayguns.items.chambers.HeatRayChamber
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.items.chambers.LifeForceChamber
import com.castlebravostudios.rayguns.items.emitters.FrostRayEmitter
import com.castlebravostudios.rayguns.items.emitters.HeatRayEmitter
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.emitters.LifeForceEmitter
import com.castlebravostudios.rayguns.items.emitters.ShrinkRayEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.items.misc._
import com.castlebravostudios.rayguns.items.misc.GlassGainMedium
import com.castlebravostudios.rayguns.items.misc.GlowstoneGainMedium
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.emitters.FortifiedSunlightEmitter
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.chambers.FortifiedSunlightChamber
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.items.emitters.ExplosiveEmitter
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.chambers.ExplosiveChamber
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.emitters.DeathRayEmitter

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

    registerItem( ExtendedBattery )
    registerItem( RefireCapacitor )
    registerItem( SolarPanel )

    registerItem( MantisBody )
    registerItem( FireflyBody )

    registerItem( ShrinkRayEmitter )
    registerItem( LaserEmitter )
    registerItem( HeatRayEmitter )
    registerItem( LifeForceEmitter )
    registerItem( FrostRayEmitter )
    registerItem( FortifiedSunlightEmitter )
    registerItem( ExplosiveEmitter )
    registerItem( DeathRayEmitter )

    registerItem( LaserChamber )
    registerItem( HeatRayChamber )
    registerItem( LifeForceChamber )
    registerItem( FrostRayChamber )
    registerItem( FortifiedSunlightChamber )
    registerItem( ExplosiveChamber )
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