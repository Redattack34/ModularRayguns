package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.LaserBeamEntity
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.EntityUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.items.emitters.HeatRayEmitter
import com.castlebravostudios.rayguns.entities.HeatRayBeamEntity
import com.castlebravostudios.rayguns.utils.RecipeRegisterer


object HeatRayChamber extends Item( Config.chamberHeatRay ) with ItemChamber {

  val moduleKey = "HeatRayChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.HeatRayChamber")
  setTextureName("rayguns:chamber_heat_ray")

  RecipeRegisterer.registerTier1Chamber(this, HeatRayEmitter)

  BeamRegistry.register({
    case GunComponents(_, HeatRayChamber, _, None, _) => { (world, player) =>
      EntityUtils.spawnNormal( world, new HeatRayBeamEntity(world), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      EntityUtils.spawnPrecise( world, new HeatRayBeamEntity( world ), player )
    }
    case GunComponents(_, HeatRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      EntityUtils.spawnScatter(world, new HeatRayBeamEntity(world), player, 9, 5 )
    }
  })
}
