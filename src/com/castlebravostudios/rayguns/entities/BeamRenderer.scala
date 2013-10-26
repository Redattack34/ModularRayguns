package com.castlebravostudios.rayguns.entities

import net.minecraft.client.renderer.entity.Render
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.Tessellator

class BeamRenderer extends Render {

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(-e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.025f, 0.025f, 1.0f)

    val tes = Tessellator.instance

    tes.startDrawingQuads();
    drawWest(tes)
    tes.draw();

    tes.startDrawingQuads();
    drawEast(tes)
    drawTop(tes)
    drawBottom(tes)
    drawSouth(tes)
    drawNorth(tes)
    tes.draw();

    GL11.glPopMatrix()
  }

  def getEntityTexture( e : Entity ) : ResourceLocation =
    new ResourceLocation( "textures/blocks/redstone_block.png" )

  private def drawWest(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 1, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 1, 0);
  }

  private def drawEast(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 0, 0);
  }

  private def drawTop(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 1, 0);
  }

  private def drawBottom(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 0, 1);
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 0, 0);
  }

  private def drawSouth(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0, 1);
    tes.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0, 0);
    tes.addVertexWithUV(1.0D, -1.0D, 1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, 1.0D, 1.0D, 1, 1);
  }

  private def drawNorth(tes: net.minecraft.client.renderer.Tessellator): Unit = {
    tes.addVertexWithUV(-1.0D, -1.0D, -1.0D, 1, 1);
    tes.addVertexWithUV(-1.0D, 1.0D, -1.0D, 1, 0);
    tes.addVertexWithUV(1.0D, 1.0D, -1.0D, 0, 0);
    tes.addVertexWithUV(1.0D, -1.0D, -1.0D, 0, 1);
  }
}