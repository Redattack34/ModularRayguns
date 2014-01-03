package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.LifeForceEffect
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens


object LifeForceChamber extends Item( Config.chamberLifeForce ) with ItemChamber {

  val moduleKey = "LifeForceChamber"
  val powerModifier = 3.0
  register
  setUnlocalizedName("rayguns.LifeForceChamber")
  setTextureName("rayguns:chamber_life_force")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.lifeForceEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, LifeForceChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, LifeForceEffect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, LifeForceChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, LifeForceEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, LifeForceChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        LifeForceEffect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, LifeForceChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( LifeForceEffect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, LifeForceChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = LifeForceEffect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, LifeForceChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = LifeForceEffect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}
