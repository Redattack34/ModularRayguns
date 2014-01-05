package com.castlebravostudios.rayguns.api.items

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.mod.ModularRayguns

trait ItemModule extends Item {

  /**
   * Get the module key for this module. This key will be stored in the NBT data
   * of the ray gun it's attached to so that the module item can be looked up
   * later.<br>
   *
   * IMPORTANT NOTE: This key must not be changed once your plugin is released!
   * Modules which cannot be found using the keys stored in NBT will be removed
   * from the ray gun.
   */
  def moduleKey : String

  /**
   * Get the power modifier for this module. The power modifiers for all four
   * modules in a gun will be multiplied together with some constant to produce
   * the power cost to fire the gun.
   */
  def powerModifier : Double

  /**
   * Get a string that will be looked up in the internationalization file
   * and used to replace an appropriate segment of the raygun name pattern.
   */
  def nameSegmentKey : String

  setMaxStackSize(1)
  setCreativeTab(ModularRayguns.raygunsTab)
}