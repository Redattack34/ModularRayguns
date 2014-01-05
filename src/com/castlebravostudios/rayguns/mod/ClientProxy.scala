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

class ClientProxy extends CommonProxy {

  override def registerRenderers() : Unit = {
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBoltEntity], new BoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[BaseBeamEntity], new BeamRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBoltEntity], new LightningBoltRenderer)
    RenderingRegistry.registerEntityRenderingHandler(classOf[LightningBeamEntity], new LightningBeamRenderer)
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