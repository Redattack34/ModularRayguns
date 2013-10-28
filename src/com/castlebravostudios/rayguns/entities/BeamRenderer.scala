package com.castlebravostudios.rayguns.entities

import net.minecraft.client.renderer.entity.Render
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.OpenGlHelper

class BeamRenderer extends Render {

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[BaseBeamEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : BaseBeamEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(-e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.025f, 0.025f, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    val tes = Tessellator.instance

    GL11.glColor4f( e.colorRed, e.colorGreen, e.colorBlue, e.colorAlpha )

    tes.startDrawingQuads();
    drawWest(tes)
    drawEast(tes)
    drawTop(tes)
    drawBottom(tes)
    drawSouth(tes)
    drawNorth(tes)
    tes.draw();

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
  }

  def getEntityTexture( e : Entity ) : ResourceLocation =
    new ResourceLocation( "rayguns", "textures/blocks/laser_bolt.png" )

  private def drawWest(tes: Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 1, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 1, 0);
  }

  private def drawEast(tes: Tessellator): Unit = {
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 0, 0);
  }

  private def drawTop(tes: Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 1, 0);
  }

  private def drawBottom(tes: Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 0, 0);
  }

  private def drawSouth(tes: Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0, 0);
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 1, 1);
  }

  private def drawNorth(tes: Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 0, 1);
  }
}