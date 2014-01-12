package com.castlebravostudios.rayguns.blocks

import cpw.mods.fml.common.registry.GameRegistry
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity

object TileEntities {

  def registerTileEntities : Unit = {

    GameRegistry.registerTileEntity(classOf[GunBenchTileEntity], "GunBench")
    GameRegistry.registerTileEntity(classOf[LensGrinderTileEntity], "LensGrinder")
  }
}