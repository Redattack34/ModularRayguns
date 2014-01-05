package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World


abstract class BaseChamber( id : Int ) extends Item( id ) with RaygunChamber {
  import RecipeRegisterer._

  setCreativeTab( ModularRayguns.raygunsTab )

  def shotEffect : BaseEffect

  def initBolt( world : World, player : EntityPlayer, bolt : BaseBoltEntity ) : Unit = initShot( world, player, bolt )
  def initBeam( world : World, player : EntityPlayer, beam : BaseBeamEntity ) : Unit = initShot( world, player, beam )
  def initShot( world : World, player : EntityPlayer, shot : Shootable ) : Unit = ()

  def createAndInitBolt( world : World, player : EntityPlayer ) : BaseBoltEntity = {
    val bolt = shotEffect.createBoltEntity(world)
    initBolt( world, player, bolt )
    bolt
  }

  def createAndInitBeam( world : World, player : EntityPlayer ) : BaseBeamEntity = {
    val beam = shotEffect.createBeamEntity(world)
    initBeam(world, player, beam)
    beam
  }

  def registerChargedShotHandler( ) : Unit = {
    BeamRegistry.register({
      case ChargeFireEvent(_, ch, _, Some(ChargeLens), _, charge ) if ch eq this => { (world, player) =>
        val bolt = createAndInitBolt(world, player )
        bolt.charge = charge
        BoltUtils.spawnNormal( world, bolt, player )
      }
      case ChargeFireEvent(_, ch, _, Some(ChargeBeamLens), _, charge ) if ch eq this => { (world, player) =>
        val beam = createAndInitBeam(world, player )
        beam.charge = charge
        BeamUtils.spawnSingleShot( beam, world, player )
      }
    })
  }

  def registerScatterShotHandler( ) : Unit = {
    BeamRegistry.register({
      case DefaultFireEvent(_, ch, _, Some(WideLens), _ ) if ch eq this => { (world, player) =>
        BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
          createAndInitBolt(world, player )
        }
      }
    })
  }

  def registerSingleShotHandlers( ) : Unit = {
    BeamRegistry.register({
      case DefaultFireEvent(_, ch, _, None, _) if ch eq this => { (world, player) =>
        BoltUtils.spawnNormal( world, createAndInitBolt( world, player ), player )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseLens), _ ) if ch eq this => { (world, player) =>
        BoltUtils.spawnPrecise( world, createAndInitBolt(world, player), player )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseBeamLens), _ ) if ch eq this => { (world, player) =>
        BeamUtils.spawnSingleShot( createAndInitBeam(world, player), world, player )
      }
    })
  }
}