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

package com.castlebravostudios.rayguns.utils

import net.minecraft.nbt.NBTTagCompound
import com.castlebravostudios.rayguns.api.items.RaygunModule
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.ShotRegistry
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.api.items.RaygunFrame
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.items.misc.RayGun
import com.castlebravostudios.rayguns.items.misc.BrokenGun
import net.minecraft.util.StatCollector
import net.minecraft.item.Item
import net.minecraft.client.resources.I18n
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.items.batteries.ItemBattery
import com.castlebravostudios.rayguns.utils.Extensions.ItemStackExtension
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions

object RaygunNbtUtils {

  val FRAME_STR = "frame"
  val BATTERY_STR = "battery"
  val CHAMBER_STR = "chamber"
  val BARREL_STR = "barrel"
  val LENS_STR = "lens"
  val ACC_STR = "accessory"

  val MODULES_TAG = "raygunModules"

  /**
   * Get the components from a stack that contains a RayGun. Returns Some if
   * all of the components in the stack are valid components and if the frame,
   * chamber and battery are all set. The returned components may or may not
   * form a valid gun.
   */
  def getComponents( item : ItemStack ) : Option[GunComponents] = {
    for { frame <- getComponent(item, FRAME_STR)(ModuleRegistry.getFrame)
          chamber <- getComponent(item, CHAMBER_STR)(ModuleRegistry.getChamber)
          battery <- getComponent(item, BATTERY_STR)(ModuleRegistry.getBattery)
          barrel <- getComponent(item, BARREL_STR)(ModuleRegistry.getBarrel)
          lens = getComponent(item, LENS_STR)(ModuleRegistry.getLens)
          accessory = getComponent(item, ACC_STR)(ModuleRegistry.getAccessory) }
      yield GunComponents( frame, chamber, battery, barrel, lens, accessory )
    }

  def getBattery( item : ItemStack ) : Option[RaygunBattery] =
    getComponent(item, BATTERY_STR)(ModuleRegistry.getBattery)

  def getComponentInfo( item : ItemStack ): Seq[String] =
    for {
      module <- getAllValidComponents( item ).components
      item <- module.item
    } yield I18n.format( item.getUnlocalizedName() + ".name" )

  private def getComponent[T <: RaygunModule](item : ItemStack, key: String )(f : String => Option[T]) : Option[T] = {
    for { name <- getModuleName( item, key )
          module <- f( name )
        } yield module
  }

  private def getModuleName( item : ItemStack, key : String ) : Option[String] =
    for {
      moduleTag <- getModuleTag(item)
      name <- Option(moduleTag.getString( key ) )
    } yield name

  private def getModuleTag( item : ItemStack ) : Option[NBTTagCompound] = {
    for {
      moduleTag <- Option( item.getTagCompoundSafe.getCompoundTag( MODULES_TAG ) )
    } yield moduleTag
  }

  def buildGun( components : GunComponents ) : Option[ItemStack] =
    if ( components.isValid && ShotRegistry.isValid( components.getFireEvent(1.0d) ) ) Some( buildValidGun( components ) )
    else None

  private def buildValidGun( components : GunComponents ) : ItemStack = {
    val stack = RayGun.asStack
    stack.stackSize = 1
    stack.setTagInfo( MODULES_TAG, buildModuleTag( components ) )
    stack
  }

  private def buildModuleTag( components : GunComponents ) : NBTTagCompound =
    buildModuleTag( OptionalGunComponents( components ) )
  private def buildModuleTag( components : OptionalGunComponents ) : NBTTagCompound = {

    def setTag( tag : NBTTagCompound, str : String )( item : RaygunModule ) : Unit =
      tag.setString(str, item.moduleKey)

    val tag = new NBTTagCompound( )
    components.frame.foreach( setTag( tag, FRAME_STR )(_) )
    components.chamber.foreach( setTag( tag, CHAMBER_STR )(_) )
    components.battery.foreach( setTag( tag, BATTERY_STR )(_) )
    components.barrel.foreach( setTag( tag, BARREL_STR )(_) )
    components.lens.foreach( setTag( tag, LENS_STR )(_) )
    components.acc.foreach( setTag( tag, ACC_STR )(_) )
    tag
  }


  /**
   * Constructs a name for the given set of components based on data in the
   * localization file.<p>
   *
   * The name is constructed by replacing sections of a pattern, looked up under
   * the localization key of "rayguns.RaygunNamePattern". This pattern contains
   * strings which will be replaced with the localized value of the nameSegmentKey
   * field of the appropriate component):<p>
   *
   * {@literal : %1$s} = chamber<br>
   * {@literal : %2$s} = battery<br>
   * {@literal : %3$s} = frame<p>
   * {@literal : %4$s} = barrel<p>
   * {@literal : %5$s} = lens, or the empty string if none<br>
   * {@literal : %6$s} = accessory, or the empty string if none<br>
   *
   * Note that not all of the replacements are used in the default en_US language file.
   */
  def getRaygunName( components : GunComponents ) : String = {
    def translate( opt : RaygunModule) : String = I18n.format( opt.nameSegmentKey )

    I18n.format("rayguns.RaygunNamePattern",
        translate( components.chamber ),
        translate( components.battery ),
        translate( components.frame ),
        translate( components.barrel ),
        components.lens.map( translate ).getOrElse(""),
        components.accessory.map( translate ).getOrElse("")
      ).replaceAll( "  ", " " ).trim
  }

  def buildBrokenGun( item : ItemStack ) : ItemStack = {
    val stack = BrokenGun.asStack
    stack.stackSize = 1
    stack.setTagInfo( MODULES_TAG, buildModuleTag( getAllValidComponents( item ) ) )
    stack
  }

  /**
   * Get whatever valid components a stack contains. This is used for salvaging
   * a broken gun and converting a functional gun to a broken one when fired if
   * it is invalid.
   */
  def getAllValidComponents( item : ItemStack ) : OptionalGunComponents = {
    val frame =     getComponent(item, FRAME_STR)   (ModuleRegistry.getFrame)
    val chamber =   getComponent(item, CHAMBER_STR)(ModuleRegistry.getChamber)
    val battery =   getComponent(item, BATTERY_STR)(ModuleRegistry.getBattery)
    val barrel =    getComponent(item, BARREL_STR) (ModuleRegistry.getBarrel)
    val lens =      getComponent(item, LENS_STR)   (ModuleRegistry.getLens)
    val accessory = getComponent(item, ACC_STR)    (ModuleRegistry.getAccessory)
    OptionalGunComponents( frame, chamber, battery, barrel, lens, accessory )
  }
}