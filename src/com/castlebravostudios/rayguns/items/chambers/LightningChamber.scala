package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.Extensions._
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.MidpointDisplacement
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.world.World
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens

object LightningChamber extends Item( Config.chamberLightning ) with ItemChamber {

  val moduleKey = "LightningChamber"
  val powerModifier = 2.0
  register
  setUnlocalizedName("rayguns.LightningChamber")
  setTextureName("rayguns:chamber_lightning")

  RecipeRegisterer.registerTier1Chamber(this, Emitters.lightningEmitter)

  private def getPointsList( world : World, player : EntityPlayer, length : Double ) : Seq[Vector3] = {
    val start = RaytraceUtils.getPlayerPosition(world, player)
    val end = RaytraceUtils.getPlayerTarget(world, player, length)

    val blocks = RaytraceUtils.rayTraceBlocks(world, start, end)( (_, _, _) => true )
    val actualEnd = blocks.headOption.map( _.hitVec ).getOrElse( end )

    MidpointDisplacement.createPositionList( new Vector3( start ), new Vector3( actualEnd ) )
  }

  BeamRegistry.register({
    case DefaultFireEvent(_, LightningChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new LightningBoltEntity(world), player )
    }
    case DefaultFireEvent(_, LightningChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new LightningBoltEntity(world), player )
    }
    case DefaultFireEvent(_, LightningChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
        new LightningBoltEntity(world)
      }
    }
    case DefaultFireEvent(_, LightningChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      val beam = new LightningBeamEntity(world)

      if ( world.isOnClient ) {
        beam.pointsList = getPointsList( world, player,  BeamUtils.maxBeamLength )
      }

      BeamUtils.spawnSingleShot( beam, world, player )
    }
    case ChargeFireEvent(_, LightningChamber, _, Some(ChargeLens), _, charge ) => { (world, player) =>
      val bolt = new LightningBoltEntity(world)
      bolt.charge = charge
      BoltUtils.spawnNormal( world, bolt, player )
    }
    case ChargeFireEvent(_, LightningChamber, _, Some(ChargeBeamLens), _, charge ) => { (world, player) =>
      val beam = new LightningBeamEntity(world)
      if ( world.isOnClient ) {
        beam.pointsList = getPointsList( world, player,  BeamUtils.maxBeamLength )
      }
      beam.charge = charge
      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}