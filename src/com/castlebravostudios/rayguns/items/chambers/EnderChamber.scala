package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.EnderBeamEntity
import com.castlebravostudios.rayguns.entities.effects.EnderBoltEntity
import com.castlebravostudios.rayguns.items.emitters.EnderEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object EnderChamber extends Item( Config.chamberEnder ) with ItemChamber {

  val moduleKey = "EnderChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.EnderChamber")
  setTextureName("rayguns:chamber_ender")

  RecipeRegisterer.registerTier2Chamber(this, EnderEmitter)

  BeamRegistry.register({
    case GunComponents(_, EnderChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new EnderBoltEntity(world), player )
    }
    case GunComponents(_, EnderChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new EnderBoltEntity( world ), player )
    }
    case GunComponents(_, EnderChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new EnderBoltEntity(world)
      }
    }
    case GunComponents(_, EnderChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new EnderBeamEntity(world), world, player )
    }
  })
}