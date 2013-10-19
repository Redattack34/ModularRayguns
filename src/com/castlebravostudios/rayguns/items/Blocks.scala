package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.blocks.gunbench.GunBench
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemBlock
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import cpw.mods.fml.common.network.NetworkRegistry
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.gui.ModularRaygunsGuiHandler

object Blocks {

  def registerBlocks : Unit = {
    val gunBench = new GunBench(1337)
    GameRegistry.registerBlock(gunBench, classOf[ItemBlock], "gunBench")
    GameRegistry.registerTileEntity(classOf[GunBenchTileEntity], "gunBenchEntity")
    NetworkRegistry.instance().registerGuiHandler(ModularRayguns, new ModularRaygunsGuiHandler)
  }
}