package com.castlebravostudios.rayguns.items.accessories

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.items.emitters.Emitters
import com.castlebravostudios.rayguns.mod.Config
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule

object RefireCapacitor extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "RefireCapacitor"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.RefireCapacitor.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.RefireCapacitor")
    .setTextureName("rayguns:refire_capacitor")

  def registerRecipe() : Unit =
    GameRegistry.addRecipe( new ItemStack( item, 1 ),
      "SI ",
      "IRI",
      "G G",
      'S' : Character, Emitters.shrinkRayEmitter,
      'I' : Character, Item.ingotIron,
      'R' : Character, Block.blockRedstone,
      'G' : Character, Item.ingotGold )
}