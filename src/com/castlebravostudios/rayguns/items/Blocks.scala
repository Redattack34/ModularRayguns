package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.blocks.gunbench.GunBench
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinder
import com.castlebravostudios.rayguns.gui.ModularRaygunsGuiHandler
import com.castlebravostudios.rayguns.mod.ModularRayguns
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemBlock
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity

object Blocks {

  def registerBlocks : Unit = {
    val gunBench = new GunBench(1337)
    GameRegistry.registerBlock(gunBench, classOf[ItemBlock], "gunBench")
    GameRegistry.registerTileEntity(classOf[GunBenchTileEntity], "gunBenchEntity")

    val lensGrinder = new LensGrinder(1338)
    GameRegistry.registerBlock(lensGrinder, classOf[ItemBlock], "lensGrinder")
    GameRegistry.registerTileEntity(classOf[LensGrinderTileEntity], "lensGrinderEntity")

    NetworkRegistry.instance().registerGuiHandler(ModularRayguns, new ModularRaygunsGuiHandler)
  }
}