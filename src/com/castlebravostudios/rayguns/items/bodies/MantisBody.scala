package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object MantisBody extends BaseRaygunModule with RaygunBody {
  val moduleKey = "MantisBody"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.MantisBody.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.MantisBody")
    .setTextureName("rayguns:body_mantis")

  def registerRecipe() : Unit =
    GameRegistry.addRecipe( new ItemStack( item, 1 ),
      "G  ",
      "IGI",
      " II",
      'G' : Character, Item.ingotGold,
      'I' : Character, Item.ingotIron )
}