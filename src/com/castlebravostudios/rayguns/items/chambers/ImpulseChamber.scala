package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.ImpulseBeamEntity
import com.castlebravostudios.rayguns.entities.effects.ImpulseBoltEntity
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

object ImpulseChamber extends Item( Config.chamberImpulse ) with ItemChamber {

  val moduleKey = "ImpulseChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.ImpulseChamber")
  setTextureName("rayguns:chamber_impulse")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.impulseEmitter)

  BeamRegistry.register({
    case GunComponents(_, ImpulseChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new ImpulseBoltEntity(world), player )
    }
    case GunComponents(_, ImpulseChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new ImpulseBoltEntity( world ), player )
    }
    case GunComponents(_, ImpulseChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new ImpulseBoltEntity(world)
      }
    }
    case GunComponents(_, ImpulseChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new ImpulseBeamEntity(world), world, player )
    }
  })
}