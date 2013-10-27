package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.entities.LifeForceBeamEntity
import com.castlebravostudios.rayguns.items.emitters.LifeForceEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.EntityUtils
import com.castlebravostudios.rayguns.utils.GunComponents

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack


object LifeForceChamber extends Item( Config.chamberLifeForce ) with ItemChamber {

  val moduleKey = "LifeForceChamber"
  val powerModifier = 3.0
  register
  setUnlocalizedName("rayguns.LifeForceChamber")
  setTextureName("rayguns:chamber_life_force")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "II ",
    "GGE",
    "II",
    'I' : Character, Item.ingotIron,
    'E' : Character, LifeForceEmitter,
    'G' : Character, Block.glass )

  BeamRegistry.register({
    case GunComponents(_, LifeForceChamber, _, None, _) => { (world, player) =>
      EntityUtils.spawnNormal( world, new LifeForceBeamEntity(world), player )
    }
    case GunComponents(_, LifeForceChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      EntityUtils.spawnPrecise( world, new LifeForceBeamEntity( world ), player )
    }
    case GunComponents(_, LifeForceChamber, _, Some(WideLens), _ ) => { (world, player) =>
      EntityUtils.spawnScatter(world, new LifeForceBeamEntity(world), player, 9, 5 )
    }
  })
}
