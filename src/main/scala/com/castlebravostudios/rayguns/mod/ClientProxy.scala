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

package com.castlebravostudios.rayguns.mod

import com.castlebravostudios.rayguns.api.EffectRegistry
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchGui
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderTileEntity

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BeamRenderer
import com.castlebravostudios.rayguns.entities.BoltRenderer
import com.castlebravostudios.rayguns.entities.LightningBeamRenderer
import com.castlebravostudios.rayguns.entities.LightningBoltRenderer
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.render.RaygunRender

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.client.MinecraftForgeClient

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.FMLCommonHandler

class ClientProxy extends CommonProxy {

  override def registerRenderers() : Unit = {
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBoltEntity], new BoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBeamEntity], new BeamRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBoltEntity], new LightningBoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBeamEntity], new LightningBeamRenderer)

    MinecraftForgeClient.registerItemRenderer(RayGun, RaygunRender)
    FMLCommonHandler.instance().bus().register(RaygunRender);
  }

  override def loadTextures() : Unit = {
    EffectRegistry.allRegisteredEffects foreach { effect =>
      loadTexture( effect.beamTexture )
      loadTexture( effect.boltTexture )
      loadTexture( effect.lineTexture )
    }
  }

  private def loadTexture( location : ResourceLocation ) : Unit = {
    val textureManager = Minecraft.getMinecraft().getTextureManager()
    if ( textureManager.getTexture( location ) == null ) {
      val simpleTex = new SimpleTexture( location )
      textureManager.loadTexture( location, simpleTex )
    }
  }

  override def getClientGuiElement( id : Int, player : EntityPlayer, world : World, x : Int, y : Int, z : Int ) : Object = {
    world.getTileEntity(x, y, z) match {
      case gunBench : GunBenchTileEntity => new GunBenchGui( player.inventory, gunBench )
      case lensGrinder : LensGrinderTileEntity => new LensGrinderGui( player.inventory, lensGrinder )
      case _ => null
    }
  }
}