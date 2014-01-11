package com.castlebravostudios.rayguns.plugins.nei

import codechicken.nei.api.IConfigureNEI
import com.castlebravostudios.rayguns.mod.ModularRayguns
import codechicken.nei.api.API
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.items.Blocks
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.items.misc.BrokenGun

class NEIModularRaygunsConfig extends IConfigureNEI{

  def loadConfig() : Unit = {
    API.hideItem( Blocks.invisibleRedstone.blockID )
    API.hideItem( RayGun.itemID )
    API.hideItem( BrokenGun.itemID )
  }

  val getName = "ModularRayguns"
  val getVersion = "1.0-Alpha1"
}