package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.LaserEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses._
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object LaserChamber extends Item( Config.chamberLaser ) with ItemChamber {

  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  RecipeRegisterer.registerTier1Chamber(this, Emitters.laserEmitter)

  BeamRegistry.register({
    case DefaultFireEvent(_, LaserChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, LaserEffect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, LaserChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, LaserEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, LaserChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        LaserEffect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, LaserChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( LaserEffect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, LaserChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = LaserEffect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, LaserChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = LaserEffect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}