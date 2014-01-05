package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object FireflyBody extends BaseRaygunModule with RaygunBody {
  val moduleKey = "FireflyBody"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.FireflyBody.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.FireflyBody")
    .setTextureName("rayguns:body_firefly")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( item, 1 ),
    "R  ",
    "IRI",
    " II",
    'R' : Character, Item.redstone,
    'I' : Character, Item.ingotIron )
}