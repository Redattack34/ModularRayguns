package com.castlebravostudios.rayguns.entities

import org.lwjgl.opengl.GL11

import com.castlebravostudios.rayguns.entities.effects.BaseEffect

import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

class BoltRenderer extends Render {
  import BoltRenderer._

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[BaseBoltEntity with BaseEffect], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : BaseBoltEntity with BaseEffect, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {

    this.bindEntityTexture(e)
    GL11.glPushMatrix()

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(-e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.025f, 0.025f, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    val tes = Tessellator.instance

    drawVertices( tes, cubeVertices )
    GL11.glScalef(1.1f, 1.1f, 1.01f)
    bindTexture( e.lineTexture )
    drawVertices( tes, reversedVertices )

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
  }

  private def drawVertices( tes : Tessellator, vertices : Seq[Vertex] ) : Unit = {
      tes.startDrawingQuads()
      vertices.foreach( ( tes.addVertexWithUV _ ).tupled )
      tes.draw()
  }

  def getEntityTexture( e : Entity ) : ResourceLocation = e match {
    case bolt : BaseBoltEntity => bolt.texture
    case _ => null
  }
}
object BoltRenderer {

  type Vertex = (Double, Double, Double, Double, Double)
  private val cubeVertices = Array[Vertex](
    (-1.0D, -1.0D, -1.0D, 0, 0),
    (-1.0D, -1.0D, 1.0D, 0, 1),
    (-1.0D, 1.0D, 1.0D, 1, 1),
    (-1.0D, 1.0D, -1.0D, 1, 0),
    (1.0D, -1.0D, 1.0D, 1, 0),
    (1.0D, -1.0D, -1.0D, 1, 1),
    (1.0D, 1.0D, -1.0D, 0, 1),
    (1.0D, 1.0D, 1.0D, 0, 0),
    (-1.0D, 1.0D, -1.0D, 0, 0),
    (-1.0D, 1.0D, 1.0D, 0, 1),
    (1.0D, 1.0D, 1.0D, 1, 1),
    (1.0D, 1.0D, -1.0D, 1, 0),
    (-1.0D, -1.0D, 1.0D, 1, 0),
    (-1.0D, -1.0D, -1.0D, 1, 1),
    (1.0D, -1.0D, -1.0D, 0, 1),
    (1.0D, -1.0D, 1.0D, 0, 0),
    (-1.0D, 1.0D, 1.0D, 0, 1),
    (-1.0D, -1.0D, 1.0D, 0, 0),
    (1.0D, -1.0D, 1.0D, 1, 0),
    (1.0D, 1.0D, 1.0D, 1, 1),
    (-1.0D, -1.0D, -1.0D, 1, 1),
    (-1.0D, 1.0D, -1.0D, 1, 0),
    (1.0D, 1.0D, -1.0D, 0, 0),
    (1.0D, -1.0D, -1.0D, 0, 1)
  )
  private val reversedVertices = cubeVertices.view.reverse

  val lineBlackTexture = new ResourceLocation( "rayguns", "textures/bolts/bolt_line_black.png" )
  val lineWhiteTexture = new ResourceLocation( "rayguns", "textures/bolts/bolt_line_white.png" )
}