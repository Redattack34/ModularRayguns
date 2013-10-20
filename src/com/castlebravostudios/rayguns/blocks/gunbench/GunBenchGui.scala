package com.castlebravostudios.rayguns.blocks.gunbench

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class GunBenchGui( playerInv: InventoryPlayer, tileEntity: GunBenchTileEntity )
  extends GuiContainer( new GunBenchContainer( playerInv, tileEntity ) ) {

  private val texture = new ResourceLocation( "rayguns", "textures/gui/container/gun_bench.png" )

  //Color is in 8-bit RGB. Hence hex. This is a sort of very dark grey.
  private[this] val color = 0x404040

  override def drawGuiContainerForegroundLayer( param1 : Int, param2 : Int ) : Unit = {
    fontRenderer.drawString("Ray Gun Bench", 8, 6, color)
    fontRenderer.drawString("Body:", 8, 23, color )
    fontRenderer.drawString("Lens:", 8, 40, color )
    fontRenderer.drawString("Accessory:", 8, 58, color )
    fontRenderer.drawString("Chamber:", 58, 23, color )
    fontRenderer.drawString("Battery:", 58, 40, color )
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