package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.api.EffectRegistry
import com.castlebravostudios.rayguns.items.chambers.Tier1CuttingChamber
import com.castlebravostudios.rayguns.items.chambers.Tier2CuttingChamber
import com.castlebravostudios.rayguns.items.chambers.Tier3CuttingChamber

object Effects {

  def registerEffects() : Unit = {
    EffectRegistry.registerEffect( Tier1CuttingChamber.effect )
    EffectRegistry.registerEffect( Tier2CuttingChamber.effect )
    EffectRegistry.registerEffect( Tier3CuttingChamber.effect )
    EffectRegistry.registerEffect( DeathRayEffect )
    EffectRegistry.registerEffect( EnderEffect )
    EffectRegistry.registerEffect( ExplosiveEffect )
    EffectRegistry.registerEffect( FortifiedSunlightEffect )
    EffectRegistry.registerEffect( FrostRayEffect )
    EffectRegistry.registerEffect( HeatRayEffect )
    EffectRegistry.registerEffect( ImpulseEffect )
    EffectRegistry.registerEffect( LaserEffect )
    EffectRegistry.registerEffect( LifeForceEffect )
    EffectRegistry.registerEffect( LightningEffect )
    EffectRegistry.registerEffect( TractorEffect )
  }
}