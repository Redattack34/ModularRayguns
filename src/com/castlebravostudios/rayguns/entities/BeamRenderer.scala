package com.castlebravostudios.rayguns.entities

import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect

class BeamRenderer extends Render {

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[BaseBeamEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : BaseBeamEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {

    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(180 - e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(180 - e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.025f * e.charge.toFloat, 0.025f * e.charge.toFloat, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glDisable(GL11.GL_CULL_FACE)
    GL11.glEnable(GL11.GL_BLEND);
    if ( e.effect == DeathRayEffect ) {
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

    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )

    for ( _ <- 0 until 3 ) {
      tes.startDrawingQuads();
      tes.addVertexWithUV(-1.0D, 0.0D, 0.0D, 0, 0);
      tes.addVertexWithUV(-1.0D, 0.0D, e.length, 0, 1);
      tes.addVertexWithUV(1.0D, 0.0D, e.length, 1, 1);
      tes.addVertexWithUV(1.0D, 0.0D, 0.0D, 1, 0);
      tes.draw();
      GL11.glRotatef(66.0f, 0.0f, 0.0f, 1.0f)
    }

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )
    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_CULL_FACE)
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
  }

  def getEntityTexture( e : Entity ) : ResourceLocation = e match {
    case beam : BaseBeamEntity => beam.effect.beamTexture
    case _ => null
  }
}