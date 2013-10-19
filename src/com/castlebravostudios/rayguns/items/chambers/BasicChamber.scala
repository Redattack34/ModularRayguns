package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.defaults.DefaultItemChamber
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.entity.projectile.EntitySnowball
import com.castlebravostudios.rayguns.items.bodies.BasicBody

class BasicChamber(id : Int) extends DefaultItemChamber( id ) {

  val moduleKey = defaultKey
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.BasicChamber")

  BeamRegistry.register({
    case GunComponents(_:BasicBody, _:BasicChamber, _, None, _) => new EntitySnowball(_, _)
  })
}