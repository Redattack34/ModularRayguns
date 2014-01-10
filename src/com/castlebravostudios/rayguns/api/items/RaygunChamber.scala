package com.castlebravostudios.rayguns.api.items

import net.minecraft.util.ResourceLocation

trait RaygunChamber extends RaygunModule {

  def registerShotHandlers() : Unit

  def chargeTexture : ResourceLocation
}