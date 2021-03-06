/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.blocks.gunbench.GunBench
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinder
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity
import com.castlebravostudios.rayguns.blocks.tech.invred.InvisibleRedstone
import com.castlebravostudios.rayguns.blocks.tech.invred.InvisibleRedstoneTileEntity
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns

import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

object RaygunsBlocks {

  var gunBench : Block = _
  var lensGrinder : Block = _
  var invisibleRedstone : Block = _

  def registerBlocks : Unit = {
    gunBench = new GunBench
    GameRegistry.registerBlock(gunBench, classOf[ItemBlock], "gunBench")
    GameRegistry.registerTileEntity(classOf[GunBenchTileEntity], "gunBenchEntity")

    lensGrinder = new LensGrinder
    GameRegistry.registerBlock(lensGrinder, classOf[ItemBlock], "lensGrinder")
    GameRegistry.registerTileEntity(classOf[LensGrinderTileEntity], "lensGrinderEntity")

    invisibleRedstone = new InvisibleRedstone
    GameRegistry.registerBlock( invisibleRedstone, classOf[ItemBlock], "invisibleRedstone" );
    GameRegistry.registerTileEntity(classOf[InvisibleRedstoneTileEntity], "invisibleRedstoneEntity" )
  }
}