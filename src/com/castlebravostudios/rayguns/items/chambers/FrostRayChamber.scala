package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.FrostRayBeamEntity
import com.castlebravostudios.rayguns.entities.effects.FrostRayBoltEntity
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item


object FrostRayChamber extends Item( Config.chamberFrostRay ) with ItemChamber {

  val moduleKey = "FrostRayChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.FrostRayChamber")
  setTextureName("rayguns:chamber_frost_ray")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.frostRayEmitter)

  BeamRegistry.register({
    case GunComponents(_, FrostRayChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new FrostRayBoltEntity(world), player )
    }
    case GunComponents(_, FrostRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new FrostRayBoltEntity( world ), player )
    }
    case GunComponents(_, FrostRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new FrostRayBoltEntity(world)
      }
    }
    case GunComponents(_, FrostRayChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new FrostRayBeamEntity(world), world, player )
    }
  })
}
