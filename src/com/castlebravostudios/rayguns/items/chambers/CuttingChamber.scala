package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.CuttingBeamEntity
import com.castlebravostudios.rayguns.entities.effects.CuttingBoltEntity
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
import com.castlebravostudios.rayguns.entities.effects.CuttingEffect
import com.castlebravostudios.rayguns.utils.DefaultFireEvent

abstract class CuttingChamber(id : Int) extends Item( id ) with ItemChamber {

  def harvestLevel : Int
  def startingPower : Float

  def init[T <: CuttingEffect]( entity : T ) : T = {
    entity.harvestLevel = harvestLevel
    entity.remainingPower = startingPower
    entity
  }

  BeamRegistry.register({
    case DefaultFireEvent(_, ch, _, None, _) if ch eq this => { (world, player) =>
      BoltUtils.spawnNormal( world, init( new CuttingBoltEntity(world) ), player )
    }
    case DefaultFireEvent(_, ch, _, Some(PreciseLens), _ ) if ch eq this => { (world, player) =>
      BoltUtils.spawnPrecise( world, init( new CuttingBoltEntity( world ) ), player )
    }
    case DefaultFireEvent(_, ch, _, Some(WideLens), _ ) if ch eq this => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        init( new CuttingBoltEntity(world) )
      }
    }
    case DefaultFireEvent(_, ch, _, Some(PreciseBeamLens), _ ) if ch eq this => { (world, player) =>
      BeamUtils.spawnSingleShot( init( new CuttingBeamEntity(world) ), world, player )
    }
  })
}
object Tier1CuttingChamber extends CuttingChamber( Config.chamberCuttingTier1 ) {
  val moduleKey = "Tier1CuttingChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.Tier1CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t1")

  val harvestLevel = 1
  val startingPower = 3.0f

  RecipeRegisterer.registerTier1Chamber(this, Emitters.tier1CuttingEmitter)
}
object Tier2CuttingChamber extends CuttingChamber( Config.chamberCuttingTier2 ) {
  val moduleKey = "Tier2CuttingChamber"
  val powerModifier = 4.0
  register
  setUnlocalizedName("rayguns.Tier2CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t2")

  val harvestLevel = 2
  val startingPower = 6.0f

  RecipeRegisterer.registerTier2Chamber(this, Emitters.tier2CuttingEmitter)
}
object Tier3CuttingChamber extends CuttingChamber( Config.chamberCuttingTier3 ) {
  val moduleKey = "Tier3CuttingChamber"
  val powerModifier = 6.0
  register
  setUnlocalizedName("rayguns.Tier3CuttingChamber")
  setTextureName("rayguns:chamber_cutting_t3")

  val harvestLevel = 3
  val startingPower = 10.0f

  RecipeRegisterer.registerTier3Chamber(this, Emitters.tier3CuttingEmitter)
}