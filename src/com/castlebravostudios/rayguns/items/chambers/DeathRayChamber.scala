package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect
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

object DeathRayChamber extends Item( Config.chamberDeathRay ) with ItemChamber {

  val moduleKey = "DeathRayChamber"
  val powerModifier = 5.0
  register
  setUnlocalizedName("rayguns.DeathRayChamber")
  setTextureName("rayguns:chamber_death_ray")

  RecipeRegisterer.registerTier3Chamber(this, Emitters.deathRayEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, DeathRayChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, DeathRayEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, DeathRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, DeathRayEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, DeathRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        DeathRayEffect.createBoltEntity( world )
      }
    }
    case DefaultFireEvent(_, DeathRayChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( DeathRayEffect.createBeamEntity( world ), world, player )
    }
  })
}