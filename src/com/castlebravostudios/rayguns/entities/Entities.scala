package com.castlebravostudios.rayguns.entities

import cpw.mods.fml.common.registry.EntityRegistry
import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.client.registry.RenderingRegistry

object Entities {

  def registerEntities : Unit = {
    EntityRegistry.registerModEntity(classOf[LaserBeamEntity], "LaserBeam",
      EntityRegistry.findGlobalUniqueEntityId(), ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[HeatRayBeamEntity], "HeatRayBeam",
      EntityRegistry.findGlobalUniqueEntityId(), ModularRayguns, 40, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBeamEntity], new BeamRenderer)
  }

}