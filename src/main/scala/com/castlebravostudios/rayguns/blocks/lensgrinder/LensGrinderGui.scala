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

package com.castlebravostudios.rayguns.blocks.lensgrinder

import org.lwjgl.opengl.GL11
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import com.castlebravostudios.rayguns.mod.ModularRayguns



class LensGrinderGui( playerInv: InventoryPlayer, tileEntity: LensGrinderTileEntity )
  extends GuiContainer( new LensGrinderContainer( playerInv, tileEntity ) ) {

  private val texture = ModularRayguns.texture( "textures/gui/container/lens_grinder.png" )

  //Color is in 8-bit RGB. Hence hex. This is a sort of very dark grey.
  private[this] val color = 0x404040

  override def drawGuiContainerForegroundLayer( param1 : Int, param2 : Int ) : Unit = {
    fontRenderer.drawString(I18n.getString("rayguns.container.grinder"), 8, 6, color)
    fontRenderer.drawString(I18n.getString("container.inventory"), 8, ySize - 96, color)
  }

  override def drawGuiContainerBackgroundLayer(par1 : Float, par2 : Int, par3: Int ) : Unit = {
    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
    mc.renderEngine.bindTexture( texture )
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2
    drawTexturedModalRect(x, y, 0, 0, xSize, ySize)

    if ( tileEntity.isGrinding ){
      val scaled = tileEntity.getTimeRemainingScaled(24)
      drawTexturedModalRect( x + 89, y + 35, 176, 0, scaled, 16)
    }
  }

}