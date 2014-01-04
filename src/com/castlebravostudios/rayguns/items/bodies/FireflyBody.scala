package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object FireflyBody extends Item( Config.fireflyBody ) with ItemBody {
  val moduleKey = "FireflyBody"
  val powerModifier = 1.0
  
  setUnlocalizedName("rayguns.FireflyBody")
  setTextureName("rayguns:body_firefly")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "R  ",
    "IRI",
    " II",
    'R' : Character, Item.redstone,
    'I' : Character, Item.ingotIron )
}