package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBeamEntity
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBoltEntity
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object ExplosiveChamber extends Item( Config.chamberExplosive ) with ItemChamber {

  val moduleKey = "ExplosiveChamber"
  val powerModifier = 10.0
  register
  setUnlocalizedName("rayguns.ExplosiveChamber")
  setTextureName("rayguns:chamber_explosive")

  RecipeRegisterer.registerTier3Chamber(this, Emitters.explosiveEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, ExplosiveChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new ExplosiveBoltEntity(world), player )
    }
    case DefaultFireEvent(_, ExplosiveChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new ExplosiveBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, ExplosiveChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new ExplosiveBeamEntity(world), world, player )
    }
  })
}