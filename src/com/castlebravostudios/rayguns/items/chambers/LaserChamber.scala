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
import com.castlebravostudios.rayguns.utils.RecipeRegisterer

object LaserChamber extends Item( Config.chamberLaser ) with ItemChamber {

  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  RecipeRegisterer.registerTier1Chamber(this, LaserEmitter)

  BeamRegistry.register({
    case GunComponents(_, LaserChamber, _, None, _) => { (world, player) =>
      EntityUtils.spawnNormal( world, new LaserBeamEntity(world), player )
    }
    case GunComponents(_, LaserChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      EntityUtils.spawnPrecise( world, new LaserBeamEntity( world ), player )
    }
    case GunComponents(_, LaserChamber, _, Some(WideLens), _ ) => { (world, player) =>
      EntityUtils.spawnScatter(world, new LaserBeamEntity(world), player, 9, 5 )
    }
  })
}