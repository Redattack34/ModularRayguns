package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.HeatRayBoltEntity
import com.castlebravostudios.rayguns.items.emitters.HeatRayEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.EntityUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item


object HeatRayChamber extends Item( Config.chamberHeatRay ) with ItemChamber {

  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.HeatRayChamber")
  setTextureName("rayguns:chamber_heat_ray")

  RecipeRegisterer.registerTier1Chamber(this, HeatRayEmitter)

  BeamRegistry.register({
    case GunComponents(_, HeatRayChamber, _, None, _) => { (world, player) =>
      EntityUtils.spawnNormal( world, new HeatRayBoltEntity(world), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      EntityUtils.spawnPrecise( world, new HeatRayBoltEntity( world ), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      EntityUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new HeatRayBoltEntity(world)
      }
    }
  })
}
