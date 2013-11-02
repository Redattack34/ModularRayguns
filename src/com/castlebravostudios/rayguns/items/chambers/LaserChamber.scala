package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.LaserBoltEntity
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

import net.minecraft.item.Item

object LaserChamber extends Item( Config.chamberLaser ) with ItemChamber {

  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  RecipeRegisterer.registerTier1Chamber(this, LaserEmitter)

  BeamRegistry.register({
    case GunComponents(_, LaserChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new LaserBoltEntity(world), player )
    }
    case GunComponents(_, LaserChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new LaserBoltEntity( world ), player )
    }
    case GunComponents(_, LaserChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new LaserBoltEntity(world)
      }
    }
  })
}