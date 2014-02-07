/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.api.items

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.api.ModuleRegistry
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.castlebravostudios.rayguns.items.misc.PrefireEvent
import com.castlebravostudios.rayguns.items.misc.GunTickEvent
import com.castlebravostudios.rayguns.items.misc.PostfireEvent
import com.castlebravostudios.rayguns.items.misc.GetFireInformationEvent

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
   * Event fired by a raygun to all modules to collect information before preparing
   * to fire. This is used for calculating the power cost, among other things.
   */
  def handleGetFireInformationEvent( event : GetFireInformationEvent ) : Unit = ()

  /**
   * Event fired by a raygun to all modules it contains just before firing. This
   * is used for checking power and rejecting the attempt to fire, among other
   * things.
   */
  def handlePrefireEvent( event : PrefireEvent ) : Unit = ()

  /**
   * Event fired by a raygun to all modules it contains just after firing. This
   * is used for subtracting power and other things.
   */
  def handlePostfireEvent( event : PostfireEvent ) : Unit = ()

  /**
   * Event fired by a raygun to all modules it contains every server tick while
   * it's in the player's inventory.
   */
  def handleTickEvent( event : GunTickEvent ) : Unit = ()
}