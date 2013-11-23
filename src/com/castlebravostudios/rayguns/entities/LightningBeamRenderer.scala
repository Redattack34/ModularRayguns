package com.castlebravostudios.rayguns.entities

import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.utils.Vector3
import com.castlebravostudios.rayguns.mod.Config

class LightningBeamRenderer extends Render {

  private val flash = Config.lightningFlash

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[LightningBeamEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : LightningBeamEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    val renderLoc = Vector3( x, y, z )

    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glDisable(GL11.GL_CULL_FACE)
    GL11.glEnable(GL11.GL_BLEND);
    if ( flash && e.renderCount % 3 == 0 ) {
      GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    else {
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    }
    GL11.glDepthMask(false);
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    val tes = Tessellator.instance

    GL11.glColor4f( e.colourRed, e.colourGreen, e.colourBlue, 1.0f )

    tes.startDrawingQuads();
    for { index <- 0 until e.pointsList.size - 1 } {
      val start = e.pointsList( index ).add(renderLoc)
      val end = e.pointsList( index + 1 ).add(renderLoc)

      val offset = 0.0625D
      tes.addVertexWithUV( start.x - offset, start.y, start.z, 0, 0);
      tes.addVertexWithUV( end.x - offset, end.y, end.z, 0, 1);
      tes.addVertexWithUV( end.x + offset, end.y, end.z, 1, 1);
      tes.addVertexWithUV( start.x + offset, start.y, start.z, 1, 0);
      tes.addVertexWithUV( start.x, start.y - offset, start.z, 0, 0);
      tes.addVertexWithUV( end.x, end.y - offset, end.z, 0, 1);
      tes.addVertexWithUV( end.x, end.y + offset, end.z, 1, 1);
      tes.addVertexWithUV( start.x, start.y + offset, start.z, 1, 0);
    }
    tes.draw();

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )
    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_CULL_FACE)
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()

    e.renderCount += 1
  }

  def getEntityTexture( e : Entity ) : ResourceLocation =
    new ResourceLocation( "rayguns", "textures/effects/blank_beam.png" )
}