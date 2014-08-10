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
import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.Vector3
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.mod.ModularRayguns
import org.lwjgl.opengl.GL14

class LightningBeamRenderer extends Render {

  private val flash = Config.lightningFlash

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[LightningBeamEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : LightningBeamEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float): Unit = {
    val renderLoc = Vector3( x, y, z )

    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glEnable(GL11.GL_BLEND);
    if ( flash && e.renderCount % 3 == 0 ) {
      GL14.glBlendEquation( GL14.GL_FUNC_REVERSE_SUBTRACT)
    }
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    GL11.glDepthMask(false);
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    this.bindTexture( e.effect.beamGlowTexture )
    drawBolt(e, renderLoc)
    this.bindTexture( e.effect.beamCoreTexture )
    drawBolt(e, renderLoc)
    this.bindTexture( e.effect.beamNoiseTexture )
    drawBolt(e, renderLoc, e.charge * 100 )

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(180 + e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(180 - e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.05f * e.charge.toFloat, 0.05f * e.charge.toFloat, 1.0f)

    BeamStartRenderer.doRender( this.renderManager.renderEngine, e, partialTickTime )

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
    GL14.glBlendEquation( GL14.GL_FUNC_ADD )

    e.renderCount += 1
  }

  private def drawBolt(e: LightningBeamEntity, renderLoc: Vector3, textureOffset : Double = 0.0d ): Int = {
    val tes = Tessellator.instance
    tes.startDrawingQuads();
    for { index <- 0 until e.pointsList.size - 1 } {
      val start = e.pointsList( index ).add(renderLoc)
      val end = e.pointsList( index + 1 ).add(renderLoc)

      val thickness = 0.0625D * e.charge
      tes.addVertexWithUV( start.x - thickness, start.y, start.z, 0, 0 + textureOffset);
      tes.addVertexWithUV( end.x - thickness, end.y, end.z, 0, 1 + textureOffset);
      tes.addVertexWithUV( end.x + thickness, end.y, end.z, 1, 1 + textureOffset);
      tes.addVertexWithUV( start.x + thickness, start.y, start.z, 1, 0 + textureOffset);

      tes.addVertexWithUV( start.x + thickness, start.y, start.z, 1, 0 + textureOffset);
      tes.addVertexWithUV( end.x + thickness, end.y, end.z, 1, 1 + textureOffset);
      tes.addVertexWithUV( end.x - thickness, end.y, end.z, 0, 1 + textureOffset);
      tes.addVertexWithUV( start.x - thickness, start.y, start.z, 0, 0 + textureOffset);

      tes.addVertexWithUV( start.x, start.y - thickness, start.z, 0, 0 + textureOffset);
      tes.addVertexWithUV( end.x, end.y - thickness, end.z, 0, 1 + textureOffset);
      tes.addVertexWithUV( end.x, end.y + thickness, end.z, 1, 1 + textureOffset);
      tes.addVertexWithUV( start.x, start.y + thickness, start.z, 1, 0 + textureOffset);

      tes.addVertexWithUV( start.x, start.y + thickness, start.z, 1, 0 + textureOffset);
      tes.addVertexWithUV( end.x, end.y + thickness, end.z, 1, 1 + textureOffset);
      tes.addVertexWithUV( end.x, end.y - thickness, end.z, 0, 1 + textureOffset);
      tes.addVertexWithUV( start.x, start.y - thickness, start.z, 0, 0 + textureOffset);
    }
    tes.draw();
  }

  def getEntityTexture( e : Entity ) : ResourceLocation =
    ModularRayguns.texture( "textures/beams/beam_glow_lightning.png" )
}