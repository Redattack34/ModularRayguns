package com.castlebravostudios.rayguns.api.items

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.mod.ModularRayguns

trait RaygunModule {

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

  /**
   * Get the item associated with this module, or null if registerItem has not
   * been called.
   */
  def item : ItemModule

  /**
   * Create the ItemModule associated with this module and register it with the
   * game under the given ID. If ID is less than or equal to zero, this method
   * should do nothing - this module has been disabled in the configuration file.
   * After this method is called, item should not return null.
   */
  def registerItem( id : Int ) : Unit

  /**
   * Register the recipe to create this item. This will be called after
   * registerItem, so it should be safe to use the item in the recipes without
   * it being null.
   */
  def registerRecipe() : Unit
}