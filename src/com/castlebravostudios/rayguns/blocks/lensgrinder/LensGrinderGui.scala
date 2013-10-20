package com.castlebravostudios.rayguns.blocks.lensgrinder

import org.lwjgl.opengl.GL11

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class LensGrinderGui( playerInv: InventoryPlayer, tileEntity: LensGrinderTileEntity )
  extends GuiContainer( new LensGrinderContainer( playerInv, tileEntity ) ) {

  private val texture = new ResourceLocation( "textures/gui/container/crafting_table.png" )

  //Color is in 8-bit RGB. Hence hex. This is a sort of very dark grey.
  private[this] val color = 0x404040

  override def drawGuiContainerForegroundLayer( param1 : Int, param2 : Int ) : Unit = {
    fontRenderer.drawString("Lens Grinder", 8, 6, color)
    fontRenderer.drawString(I18n.getString("container.inventory"), 8, ySize - 96, color)
  }

  override def drawGuiContainerBackgroundLayer(par1 : Float, par2 : Int, par3: Int ) : Unit = {
    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
    mc.renderEngine.bindTexture( texture )
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2
    drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
  }

}