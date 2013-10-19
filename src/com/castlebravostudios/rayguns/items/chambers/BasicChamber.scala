package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.defaults.DefaultItemChamber
import com.castlebravostudios.rayguns.items.bodies.BasicBody
import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.entity.projectile.EntitySnowball
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens

class BasicChamber(id : Int) extends DefaultItemChamber( id ) {

  val moduleKey = "BasicChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.BasicChamber")

  BeamRegistry.register({
    case GunComponents(_, _:BasicChamber, _, None, _) => { (world, player) =>
      world.spawnEntityInWorld( new EntitySnowball(world, player) )
    }
    case GunComponents(_, _:BasicChamber, _, Some(_:PreciseLens), _ ) => { (world, player) =>
      val snowball = new EntitySnowball( world, player )
      snowball.motionX *= 2
      snowball.motionY *= 2
      snowball.motionZ *= 2
      world.spawnEntityInWorld( snowball )
    }
    case GunComponents(_, _:BasicChamber, _, Some(_:WideLens), _ ) => { (world, player) =>
      for{ x <- -1 to 1
           y <- -1 to 1
      } {
        val snowball = new EntitySnowball( world, player )
        snowball.motionX *= 1 + (x * 0.5 )
        snowball.motionY *= 1 + (y * 0.5 )
        world.spawnEntityInWorld( snowball )
      }
    }
  })
}