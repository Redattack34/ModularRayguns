package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.ImpulseEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens

object ImpulseChamber extends Item( Config.chamberImpulse ) with ItemChamber {

  val moduleKey = "ImpulseChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.ImpulseChamber")
  setTextureName("rayguns:chamber_impulse")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.impulseEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, ImpulseChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, ImpulseEffect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, ImpulseChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, ImpulseEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, ImpulseChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        ImpulseEffect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, ImpulseChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( ImpulseEffect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, ImpulseChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = ImpulseEffect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, ImpulseChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = ImpulseEffect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}