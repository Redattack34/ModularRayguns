package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.CuttingEffect
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

abstract class CuttingChamber(id : Int) extends Item( id ) with ItemChamber {

  def effect : CuttingEffect

  BeamRegistry.register({
    case DefaultFireEvent(_, ch, _, None, _) if ch eq this => { (world, player) =>
      BoltUtils.spawnNormal( world, effect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, ch, _, Some(PreciseLens), _ ) if ch eq this => { (world, player) =>
      BoltUtils.spawnPrecise( world, effect.createBoltEntity(world), player )
    }
    case DefaultFireEvent(_, ch, _, Some(WideLens), _ ) if ch eq this => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        effect.createBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, ch, _, Some(PreciseBeamLens), _ ) if ch eq this => { (world, player) =>
      BeamUtils.spawnSingleShot( effect.createBeamEntity(world), world, player )
    }
    case ChargeFireEvent(_, ch, _, Some(ChargeLens), _, charge ) if ch eq this => { (world, player) =>
      val bolt = effect.createBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, ch, _, Some(ChargeBeamLens), _, charge ) if ch eq this => { (world, player) =>
      val beam = effect.createBeamEntity(world)
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}
object Tier1CuttingChamber extends CuttingChamber( Config.chamberCuttingTier1 ) {
  val moduleKey = "Tier1CuttingChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.Tier1CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t1")

  val effect = new CuttingEffect( "Tier1Cutting", 1, 3.0f )

  RecipeRegisterer.registerTier1Chamber(this, Emitters.tier1CuttingEmitter)
}
object Tier2CuttingChamber extends CuttingChamber( Config.chamberCuttingTier2 ) {
  val moduleKey = "Tier2CuttingChamber"
  val powerModifier = 4.0
  register
  setUnlocalizedName("rayguns.Tier2CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t2")

  val effect = new CuttingEffect( "Tier2Cutting", 2, 4.5f )

  RecipeRegisterer.registerTier2Chamber(this, Emitters.tier2CuttingEmitter)
}
object Tier3CuttingChamber extends CuttingChamber( Config.chamberCuttingTier3 ) {
  val moduleKey = "Tier3CuttingChamber"
  val powerModifier = 6.0
  register
  setUnlocalizedName("rayguns.Tier3CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t3")

  val effect = new CuttingEffect( "Tier3Cutting", 3, 6.0f )

  RecipeRegisterer.registerTier3Chamber(this, Emitters.tier3CuttingEmitter)
}