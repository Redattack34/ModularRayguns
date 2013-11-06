package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
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
import com.castlebravostudios.rayguns.items.emitters.ExplosiveEmitter
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBoltEntity
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBeamEntity

object ExplosiveChamber extends Item( Config.chamberExplosive ) with ItemChamber {

  val moduleKey = "ExplosiveChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.explosiveChamber")
  setTextureName("rayguns:chamber_explosive")

  RecipeRegisterer.registerTier3Chamber(this, ExplosiveEmitter)

  BeamRegistry.register({
    case GunComponents(_, ExplosiveChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new ExplosiveBoltEntity(world), player )
    }
    case GunComponents(_, ExplosiveChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new ExplosiveBoltEntity( world ), player )
    }
    case GunComponents(_, ExplosiveChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new ExplosiveBeamEntity(world), world, player )
    }
  })
}