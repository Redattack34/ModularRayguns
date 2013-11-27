package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.items.emitters.ImpulseEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.utils.BeamUtils
import net.minecraft.client.particle.EntityFX
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LaserBoltEntity
import com.castlebravostudios.rayguns.entities.effects.LaserBeamEntity

object ImpulseChamber extends Item( Config.chamberImpulse ) with ItemChamber {

  val moduleKey = "ImpulseChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.ImpulseChamber")
  setTextureName("rayguns:chamber_impulse")

  RecipeRegisterer.registerTier1Chamber(this, ImpulseEmitter)

  BeamRegistry.register({
    case GunComponents(_, ImpulseChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new LaserBoltEntity(world), player )
    }
    case GunComponents(_, ImpulseChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new LaserBoltEntity( world ), player )
    }
    case GunComponents(_, ImpulseChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new LaserBoltEntity(world)
      }
    }
    case GunComponents(_, ImpulseChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new LaserBeamEntity(world), world, player )
    }
  })
}