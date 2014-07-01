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

package com.castlebravostudios.rayguns.blocks.gunbench

import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side

@SideOnly(Side.CLIENT)
class GunBenchGui( playerInv: InventoryPlayer, tileEntity: GunBenchTileEntity )
  extends GuiContainer( new GunBenchContainer( playerInv, tileEntity ) ) {

  private val texture = ModularRayguns.texture( "textures/gui/container/gun_bench.png" )

  //Color is in 8-bit RGB. Hence hex. This is a sort of very dark grey.
  private[this] val color = 0x404040

  this.ySize = 185

  override def drawGuiContainerForegroundLayer( param1 : Int, param2 : Int ) : Unit = {
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench"), 8, 6, color)
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.frame"), 8, 23, color )
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.lens"), 8, 40, color )
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.chamber"), 65, 23, color )
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.battery"), 65, 40, color )
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.acc"), 8, 59, color )
    fontRendererObj.drawString(I18n.format("rayguns.container.gunbench.barrel"), 8, 76, color )
    fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96, color)
  }

  override def drawGuiContainerBackgroundLayer(par1 : Float, par2 : Int, par3: Int ) : Unit = {
    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
    mc.renderEngine.bindTexture( texture )
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2
    drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
  }

}