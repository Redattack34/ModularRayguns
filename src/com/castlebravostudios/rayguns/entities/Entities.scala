package com.castlebravostudios.rayguns.entities

import cpw.mods.fml.common.registry.EntityRegistry
import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraft.entity.Entity

object Entities {

  private val renderer = new BoltRenderer

  def registerEntities : Unit = {
    registerEntity( classOf[LaserBoltEntity], "LaserBolt", 0 )
    registerEntity( classOf[HeatRayBoltEntity], "HeatRayBolt", 1 )
    registerEntity( classOf[LifeForceBoltEntity], "LifeForceBolt", 2 )
    registerEntity( classOf[FrostRayBoltEntity], "FrostRayBolt", 3 )
  }

  private def registerEntity[T <: Entity]( cls : Class[T], name : String, id : Int ) : Unit = {
    EntityRegistry.registerModEntity(cls, name,
      id, ModularRayguns, 40, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(cls, renderer)
  }

}