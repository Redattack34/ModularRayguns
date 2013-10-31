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
import com.castlebravostudios.rayguns.items.emitters.FrostRayEmitter
import com.castlebravostudios.rayguns.entities.FrostRayBeamEntity
import com.castlebravostudios.rayguns.utils.RecipeRegisterer


object FrostRayChamber extends Item( Config.chamberFrostRay ) with ItemChamber {

  val moduleKey = "FrostRayChamber"
  val powerModifier = 1.5
  register
  setUnlocalizedName("rayguns.FrostRayChamber")
  setTextureName("rayguns:chamber_frost_ray")

  RecipeRegisterer.registerTier2Chamber(this, FrostRayEmitter)

  BeamRegistry.register({
    case GunComponents(_, FrostRayChamber, _, None, _) => { (world, player) =>
      EntityUtils.spawnNormal( world, new FrostRayBeamEntity(world), player )
    }
    case GunComponents(_, FrostRayChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      EntityUtils.spawnPrecise( world, new FrostRayBeamEntity( world ), player )
    }
    case GunComponents(_, FrostRayChamber, _, Some(WideLens), _ ) => { (world, player) =>
      EntityUtils.spawnScatter(world, new FrostRayBeamEntity(world), player, 9, 5 )
    }
  })
}
