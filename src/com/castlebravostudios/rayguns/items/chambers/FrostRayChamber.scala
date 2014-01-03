package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.FrostRayEffect
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


object FrostRayChamber extends Item( Config.chamberFrostRay ) with ItemChamber {

  val moduleKey = "FrostRayChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.FrostRayChamber")
  setTextureName("rayguns:chamber_frost_ray")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.frostRayEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, FrostRayChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, FrostRayEffect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, FrostRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, FrostRayEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, FrostRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        FrostRayEffect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, FrostRayChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( FrostRayEffect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, FrostRayChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = FrostRayEffect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, FrostRayChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = FrostRayEffect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}
