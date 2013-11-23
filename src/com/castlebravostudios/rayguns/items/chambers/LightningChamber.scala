package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import com.castlebravostudios.rayguns.items.emitters.LightningEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.world.World
import cpw.mods.fml.common.network.Player
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import net.minecraft.entity.player.EntityPlayer
import scala.collection.SortedSet
import com.castlebravostudios.rayguns.utils.MidpointDisplacement
import com.castlebravostudios.rayguns.utils.Extensions._

object LightningChamber extends Item( Config.chamberLightning ) with ItemChamber {

  val moduleKey = "LightningChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.LightningChamber")
  setTextureName("rayguns:chamber_lightning")

  RecipeRegisterer.registerTier1Chamber(this, LightningEmitter)

  private def getPointsList( world : World, player : EntityPlayer, length : Double ) : Seq[Vector3] = {
    val start = RaytraceUtils.getPlayerPosition(world, player)
    val end = RaytraceUtils.getPlayerTarget(world, player, length)

    val blocks = RaytraceUtils.rayTraceBlocks(world, start, end)( (_, _, _) => true )
    val actualEnd = blocks.headOption.map( _.hitVec ).getOrElse( end )

    MidpointDisplacement.createPositionList( new Vector3( start ), new Vector3( actualEnd ) )
  }

  BeamRegistry.register({
    case GunComponents(_, LightningChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new LightningBoltEntity(world), player )
    }
    case GunComponents(_, LightningChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new LightningBoltEntity(world), player )
    }
    case GunComponents(_, LightningChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new LightningBoltEntity(world)
      }
    }
    case GunComponents(_, LightningChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      val beam = new LightningBeamEntity(world)

      if ( world.isOnClient ) {
        beam.pointsList = getPointsList( world, player,  BeamUtils.maxBeamLength )
      }

      BeamUtils.spawnSingleShot( beam, world, player )
    }
  })
}