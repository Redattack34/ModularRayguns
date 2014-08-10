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

import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.renderer.Tessellator
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14

object BeamStartRenderer {
  def doRender( textureManager: TextureManager, e: BaseBeamEntity, partialTickTime: Float) = {

    GL11.glPushMatrix()
    GL11.glScalef(1.0f, 1.0f, 0.05f * e.charge.toFloat )

    if ( e.effect.glowSubtractsColor ) {
      GL14.glBlendEquation( GL14.GL_FUNC_REVERSE_SUBTRACT)
    }
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)

    textureManager.bindTexture( e.effect.beamStartGlowTexture )
    drawQuads( 1.0 )

    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO)
    GL14.glBlendEquation( GL14.GL_FUNC_ADD )

    if ( e.effect.coreSubtractsColor ) {
      GL14.glBlendEquation( GL14.GL_FUNC_REVERSE_SUBTRACT)
    }
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)

    textureManager.bindTexture( e.effect.beamStartCoreTexture )
    drawQuads( 1.0 )

    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO)
    GL14.glBlendEquation( GL14.GL_FUNC_ADD )

    GL11.glPopMatrix()
  }

  def drawQuads( size : Double ) = {
    val tes = Tessellator.instance
    tes.startDrawingQuads()
    tes.addVertexWithUV(-size, size, 0.0D, 0, 0)
    tes.addVertexWithUV(size, size, 0.0d, 0, 1 )
    tes.addVertexWithUV(size, -size, 0.0d, 1, 1 )
    tes.addVertexWithUV(-size, -size, 0.0D, 1, 0)

    tes.addVertexWithUV(-size, -size, 0.0D, 1, 0)
    tes.addVertexWithUV(size, -size, 0.0d, 1, 1 )
    tes.addVertexWithUV(size, size, 0.0d, 0, 1 )
    tes.addVertexWithUV(-size, size, 0.0D, 0, 0)

    tes.addVertexWithUV(-size, 0.0D, size, 0, 0)
    tes.addVertexWithUV(size, 0.0D, size, 0, 1 )
    tes.addVertexWithUV(size, 0.0D, -size, 1, 1 )
    tes.addVertexWithUV(-size, 0.0D, -size, 1, 0)

    tes.addVertexWithUV(-size, 0.0D, -size, 1, 0)
    tes.addVertexWithUV(size, 0.0D, -size, 1, 1 )
    tes.addVertexWithUV(size, 0.0D, size, 0, 1 )
    tes.addVertexWithUV(-size, 0.0D, size, 0, 0)

    tes.addVertexWithUV(0.0D, -size, size, 0, 0)
    tes.addVertexWithUV(0.0D, size, size, 0, 1 )
    tes.addVertexWithUV(0.0D, size, -size, 1, 1 )
    tes.addVertexWithUV(0.0D, -size, -size, 1, 0)

    tes.addVertexWithUV(0.0D, -size, -size, 1, 0)
    tes.addVertexWithUV(0.0D, size, -size, 1, 1 )
    tes.addVertexWithUV(0.0D, size, size, 0, 1 )
    tes.addVertexWithUV(0.0D, -size, size, 0, 0)

    tes.draw()
  }
}