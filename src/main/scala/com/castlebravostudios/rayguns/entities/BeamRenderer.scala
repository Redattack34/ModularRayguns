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
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.entities.effects.DeathRayEffect
import org.lwjgl.opengl.GL14

class BeamRenderer extends Render {

  private[this] val half = 1.0d / 2.0d
  private[this] val root3Over2 = Math.sqrt( 3.0d ) / 2.0d

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[BaseBeamEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : BaseBeamEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float): Unit = {

    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(180 + e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(180 - e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(0.05f * e.charge.toFloat, 0.05f * e.charge.toFloat, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glDepthMask(false);
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    drawGlow( e )
    drawCore( e )

    GL11.glPopMatrix()
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_LIGHTING)
  }

  private def drawGlow(e: BaseBeamEntity): Unit = {
    this.bindTexture( e.effect.beamGlowTexture );
    if ( e.effect.glowSubtractsColor ) {
      GL14.glBlendEquation( GL14.GL_FUNC_REVERSE_SUBTRACT)
    }
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    val tes = Tessellator.instance
    tes.startDrawingQuads();
    drawQuad( 1.0, 0.0, e.length )
    drawQuad( half, root3Over2, e.length )
    drawQuad( half, -root3Over2, e.length )
    tes.draw();
    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);
    GL14.glBlendEquation( GL14.GL_FUNC_ADD )
  }

  private def drawCore(e: BaseBeamEntity): Unit = {
    this.bindTexture( e.effect.beamCoreTexture );
    if ( e.effect.coreSubtractsColor ) {
      GL14.glBlendEquation( GL14.GL_FUNC_REVERSE_SUBTRACT)
    }
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    val tes = Tessellator.instance
    tes.startDrawingQuads();
    drawQuad( 1.0, 0.0, e.length )
    drawQuad( half, root3Over2, e.length )
    drawQuad( half, -root3Over2, e.length )
    tes.draw();
    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);
    GL14.glBlendEquation( GL14.GL_FUNC_ADD )
  }


  private def drawQuad( x : Double, y : Double, length : Double ): Unit = {
    val tes = Tessellator.instance
    tes.addVertexWithUV(-x, y, 0.0D, 0, 0);
    tes.addVertexWithUV(-x, y, length, 0, 1);
    tes.addVertexWithUV(x, -y, length, 1, 1);
    tes.addVertexWithUV(x, -y, 0.0D, 1, 0);

    tes.addVertexWithUV(x, -y, 0.0D, 1, 0);
    tes.addVertexWithUV(x, -y, length, 1, 1);
    tes.addVertexWithUV(-x, y, length, 0, 1);
    tes.addVertexWithUV(-x, y, 0.0D, 0, 0);
  }

  def getEntityTexture( e : Entity ) : ResourceLocation = e match {
    case beam : BaseBeamEntity => beam.effect.beamGlowTexture
    case _ => null
  }
}