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

package com.castlebravostudios.rayguns.entities

import org.lwjgl.opengl.GL11

import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.entities.BoltRenderer.Vertex

import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

class BoltRenderer extends Render {

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[BaseBoltEntity with BaseEffect], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : BaseBoltEntity with BaseEffect, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {

    this.bindEntityTexture(e)
    GL11.glPushMatrix()

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(-e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.025f * e.charge.toFloat, 0.025f * e.charge.toFloat, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    val tes = Tessellator.instance

    drawVertices( tes, BoltRenderer.cubeVertices )
    GL11.glScalef(1.1f, 1.1f, 1.01f)
    bindTexture( e.effect.lineTexture )
    drawVertices( tes, BoltRenderer.reversedVertices )

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
    case bolt : BaseBoltEntity => bolt.effect.boltTexture
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