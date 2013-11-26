package com.castlebravostudios.rayguns.blocks.tech.invred

import net.minecraft.tileentity.TileEntity

class InvisibleRedstoneTileEntity extends TileEntity {

  private var ticks = 0

  override def updateEntity() : Unit = {
    if ( hasWorldObj() && ticks >= 1 ) {
      getWorldObj().setBlockToAir(xCoord, yCoord, zCoord)
      invalidate()
    }

    ticks += 1
  }
}