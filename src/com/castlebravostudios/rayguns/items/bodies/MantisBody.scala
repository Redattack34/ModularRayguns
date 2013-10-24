package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.item.Item
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.ItemStack

object MantisBody extends Item( Config.mantisBody ) with ItemBody {

  val moduleKey = "MantisBody"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.MantisBody")
  setTextureName("rayguns:body_mantis")

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "G  ",
    "IGI",
    " II",
    'G' : Character, Item.ingotGold,
    'I' : Character, Item.ingotIron )
}