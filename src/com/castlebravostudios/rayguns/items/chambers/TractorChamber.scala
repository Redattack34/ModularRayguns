package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.TractorBeamEntity
import com.castlebravostudios.rayguns.entities.effects.TractorBoltEntity
import com.castlebravostudios.rayguns.items.emitters.TractorEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object TractorChamber extends Item( Config.chamberTractor ) with ItemChamber {

  val moduleKey = "TractorChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.TractorChamber")
  setTextureName("rayguns:chamber_tractor")

  RecipeRegisterer.registerTier2Chamber(this, TractorEmitter)

  BeamRegistry.register({
    case GunComponents(_, TractorChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new TractorBoltEntity(world), player )
    }
    case GunComponents(_, TractorChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new TractorBoltEntity( world ), player )
    }
    case GunComponents(_, TractorChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new TractorBoltEntity(world)
      }
    }
    case GunComponents(_, TractorChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new TractorBeamEntity(world), world, player )
    }
  })
}