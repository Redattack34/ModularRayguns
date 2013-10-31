package com.castlebravostudios.rayguns.entities

import cpw.mods.fml.common.registry.EntityRegistry
import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraft.entity.Entity

object Entities {

  private val renderer = new BeamRenderer

  def registerEntities : Unit = {
    registerEntity( classOf[LaserBeamEntity], "LaserBeam", 0 )
    registerEntity( classOf[HeatRayBeamEntity], "HeatRayBeam", 1 )
    registerEntity( classOf[LifeForceBeamEntity], "LifeForceBeam", 2 )
    registerEntity( classOf[FrostRayBeamEntity], "FrostRayBeam", 3 )
  }

  private def registerEntity[T <: Entity]( cls : Class[T], name : String, id : Int ) : Unit = {
    EntityRegistry.registerModEntity(cls, name,
      id, ModularRayguns, 40, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(cls, renderer)
  }

}