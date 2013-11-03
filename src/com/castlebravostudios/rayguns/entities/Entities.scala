package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.beams.LaserBeam
import com.castlebravostudios.rayguns.entities.effects._
import com.castlebravostudios.rayguns.mod.ModularRayguns

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.entity.Entity

object Entities {

  private val boltRenderer = new BoltRenderer
  private val beamRenderer = new BeamRenderer

  def registerEntities : Unit = {
    registerBoltEntity( classOf[LaserBoltEntity], "LaserBolt", 0 )
    registerBoltEntity( classOf[HeatRayBoltEntity], "HeatRayBolt", 1 )
    registerBoltEntity( classOf[LifeForceBoltEntity], "LifeForceBolt", 2 )
    registerBoltEntity( classOf[FrostRayBoltEntity], "FrostRayBolt", 3 )
    registerBoltEntity( classOf[FortifiedSunlightBoltEntity], "FortifiedSunlightBolt", 4 )

    registerBeamEntity( classOf[LaserBeam], "LaserBeam", 20 )
  }

  private def registerBoltEntity[T <: Entity]( cls : Class[T], name : String, id : Int ) : Unit = {
    EntityRegistry.registerModEntity(cls, name,
      id, ModularRayguns, 40, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(cls, boltRenderer)
  }

  private def registerBeamEntity[T <: Entity]( cls : Class[T], name : String, id : Int ) : Unit = {
    EntityRegistry.registerModEntity(cls, name,
      id, ModularRayguns, 40, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(cls, beamRenderer)
  }
}