package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.entities.effects._

object Entities {

  def registerEntities : Unit = {
    EntityRegistry.registerModEntity(classOf[BaseBoltEntity], "BoltEntity", 1, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[BaseBoltEntity], "BeamEntity", 2, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[LightningBoltEntity], "LightningBolt", 3, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[LightningBeamEntity], "LightningBeam", 4, ModularRayguns, 40, 1, true)
  }
}