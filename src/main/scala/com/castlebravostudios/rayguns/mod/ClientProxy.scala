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

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BeamRenderer
import com.castlebravostudios.rayguns.entities.BoltRenderer
import com.castlebravostudios.rayguns.entities.LightningBeamRenderer
import com.castlebravostudios.rayguns.entities.LightningBoltRenderer
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import cpw.mods.fml.client.registry.RenderingRegistry
import com.castlebravostudios.rayguns.api.EffectRegistry
import net.minecraft.util.ResourceLocation
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraftforge.client.MinecraftForgeClient
import com.castlebravostudios.rayguns.render.RaygunRender
import com.castlebravostudios.rayguns.items.misc.RayGun
import cpw.mods.fml.common.registry.TickRegistry
import cpw.mods.fml.relauncher.Side

class ClientProxy extends CommonProxy {

  override def registerRenderers() : Unit = {
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBoltEntity], new BoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBeamEntity], new BeamRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBoltEntity], new LightningBoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBeamEntity], new LightningBeamRenderer)

    MinecraftForgeClient.registerItemRenderer(RayGun.itemID, RaygunRender)
    TickRegistry.registerTickHandler(RaygunRender, Side.CLIENT)
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
}