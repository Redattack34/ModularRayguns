package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.TractorBeamEntity
import com.castlebravostudios.rayguns.entities.effects.TractorBoltEntity
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

object TractorChamber extends Item( Config.chamberTractor ) with ItemChamber {

  val moduleKey = "TractorChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.TractorChamber")
  setTextureName("rayguns:chamber_tractor")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.tractorEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, TractorChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new TractorBoltEntity(world), player )
    }
    case DefaultFireEvent(_, TractorChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new TractorBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, TractorChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new TractorBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, TractorChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new TractorBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, TractorChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = new TractorBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, TractorChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = new TractorBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}