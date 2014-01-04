package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningEffect
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.MidpointDisplacement
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.utils.Vector3

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object LightningChamber extends BaseChamber( Config.chamberLightning ) {

  val moduleKey = "LightningChamber"
  val powerModifier = 2.0
  val shotEffect = LightningEffect

  setUnlocalizedName("rayguns.LightningChamber")
  setTextureName("rayguns:chamber_lightning")

  ModuleRegistry.registerModule(this)
  RecipeRegisterer.registerChamber( Tier1, this, Emitters.lightningEmitter)

  registerSingleShotHandlers()
  registerScatterShotHandler()
  registerChargedShotHandler()

  override def initBeam( world : World, player : EntityPlayer, beam : BaseBeamEntity ) : Unit = {
    if ( world.isOnClient ) {
      beam.asInstanceOf[LightningBeamEntity].pointsList = getPointsList( world, player )
    }
  }

  private def getPointsList( world : World, player : EntityPlayer ) : Seq[Vector3] = {
    val start = RaytraceUtils.getPlayerPosition(world, player)
    val end = RaytraceUtils.getPlayerTarget(world, player, BeamUtils.maxBeamLength)

    val blocks = RaytraceUtils.rayTraceBlocks(world, start, end)( (_, _, _) => true )
    val actualEnd = blocks.headOption.map( _.hitVec ).getOrElse( end )

    MidpointDisplacement.createPositionList( new Vector3( start ), new Vector3( actualEnd ) )
  }
}