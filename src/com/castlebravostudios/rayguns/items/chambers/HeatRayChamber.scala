package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.items.emitters.HeatRayEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.entities.effects.HeatRayBoltEntity


object HeatRayChamber extends Item( Config.chamberHeatRay ) with ItemChamber {

  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.HeatRayChamber")
  setTextureName("rayguns:chamber_heat_ray")

  RecipeRegisterer.registerTier1Chamber(this, HeatRayEmitter)

  BeamRegistry.register({
    case GunComponents(_, HeatRayChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new HeatRayBoltEntity(world), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new HeatRayBoltEntity( world ), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new HeatRayBoltEntity(world)
      }
    }
  })
}
