package com.castlebravostudios.rayguns.gui

import net.minecraft.client.gui.GuiScreen

class GuiBasic extends GuiScreen {
  override def drawScreen( par1: Int, par2: Int, par3: Float ) : Unit = {
    drawDefaultBackground()
  }
}
object GuiBasic {
  val GUI_ID : Int = 20;
}