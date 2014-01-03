package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.EnderEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object EnderChamber extends Item( Config.chamberEnder ) with ItemChamber {

  val moduleKey = "EnderChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.EnderChamber")
  setTextureName("rayguns:chamber_ender")

  RecipeRegisterer.registerTier2Chamber(this, Emitters.enderEmitter )

  BeamRegistry.register({
    case DefaultFireEvent(_, EnderChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, EnderEffect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, EnderChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, EnderEffect.createBoltEntity( world ), player )
    }
    case DefaultFireEvent(_, EnderChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        EnderEffect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, EnderChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( EnderEffect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, EnderChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = EnderEffect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, EnderChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = EnderEffect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}