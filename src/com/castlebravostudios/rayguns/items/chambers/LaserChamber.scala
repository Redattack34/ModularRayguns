package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.entity.projectile.EntitySnowball
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens

object LaserChamber extends Item( Config.chamberLaser ) with ItemChamber {

  val moduleKey = "LaserChamber"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.LaserChamber")
  setTextureName("rayguns:chamber_laser")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "II ",
    "GGE",
    "II",
    'I' : Character, Item.ingotIron,
    'E' : Character, LaserEmitter,
    'G' : Character, Block.glass )

  BeamRegistry.register({
    case GunComponents(_, LaserChamber, _, None, _) => { (world, player) =>
      world.spawnEntityInWorld( new EntitySnowball(world, player) )
    }
    case GunComponents(_, LaserChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      val snowball = new EntitySnowball( world, player )
      snowball.motionX *= 2
      snowball.motionY *= 2
      snowball.motionZ *= 2
      world.spawnEntityInWorld( snowball )
    }
    case GunComponents(_, LaserChamber, _, Some(WideLens), _ ) => { (world, player) =>
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