package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightBoltEntity
import com.castlebravostudios.rayguns.entities.effects.FrostRayBoltEntity
import com.castlebravostudios.rayguns.entities.effects.HeatRayBoltEntity
import com.castlebravostudios.rayguns.entities.effects.LaserBoltEntity
import com.castlebravostudios.rayguns.entities.effects.LifeForceBoltEntity
import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.entities.effects.LaserBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LifeForceBeamEntity
import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightBeamEntity
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBeamEntity
import com.castlebravostudios.rayguns.entities.effects.FrostRayBeamEntity
import com.castlebravostudios.rayguns.entities.effects.HeatRayBeamEntity
import com.castlebravostudios.rayguns.entities.effects.ExplosiveBoltEntity

object Entities {

  private val boltRenderer = new BoltRenderer
  private val beamRenderer = new BeamRenderer

  def registerEntities : Unit = {
    registerBoltEntity( classOf[LaserBoltEntity], "LaserBolt", 0 )
    registerBoltEntity( classOf[HeatRayBoltEntity], "HeatRayBolt", 1 )
    registerBoltEntity( classOf[LifeForceBoltEntity], "LifeForceBolt", 2 )
    registerBoltEntity( classOf[FrostRayBoltEntity], "FrostRayBolt", 3 )
    registerBoltEntity( classOf[FortifiedSunlightBoltEntity], "FortifiedSunlightBolt", 4 )
    registerBoltEntity( classOf[ExplosiveBoltEntity], "ExplosiveBolt", 5 )

    registerBeamEntity( classOf[LaserBeamEntity], "LaserBeam", 20 )
    registerBeamEntity( classOf[HeatRayBeamEntity], "HeatRayBeam", 21 )
    registerBeamEntity( classOf[LifeForceBeamEntity], "LifeForceBeam", 22 )
    registerBeamEntity( classOf[FrostRayBeamEntity], "FrostRayBeam", 23 )
    registerBeamEntity( classOf[FortifiedSunlightBeamEntity], "FortifiedSunlightBeam", 24 )
    registerBeamEntity( classOf[ExplosiveBeamEntity], "ExplosiveBeam", 25 )
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