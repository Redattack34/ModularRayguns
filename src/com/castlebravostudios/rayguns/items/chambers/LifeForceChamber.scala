package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.LifeForceBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LifeForceBoltEntity
import com.castlebravostudios.rayguns.items.emitters.LifeForceEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item


object LifeForceChamber extends Item( Config.chamberLifeForce ) with ItemChamber {

  val moduleKey = "LifeForceChamber"
  val powerModifier = 3.0
  register
  setUnlocalizedName("rayguns.LifeForceChamber")
  setTextureName("rayguns:chamber_life_force")

  RecipeRegisterer.registerTier2Chamber(this, LifeForceEmitter)

  BeamRegistry.register({
    case GunComponents(_, LifeForceChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new LifeForceBoltEntity(world), player )
    }
    case GunComponents(_, LifeForceChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new LifeForceBoltEntity( world ), player )
    }
    case GunComponents(_, LifeForceChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new LifeForceBoltEntity(world)
      }
    }
    case GunComponents(_, LifeForceChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new LifeForceBeamEntity(world), world, player )
    }
  })
}
