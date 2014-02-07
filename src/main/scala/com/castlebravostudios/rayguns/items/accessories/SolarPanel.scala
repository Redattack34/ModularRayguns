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

package com.castlebravostudios.rayguns.items.accessories

import java.util.Random
import scala.collection.mutable.WeakHashMap
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import com.castlebravostudios.rayguns.items.misc.GunTickEvent

object SolarPanel extends BaseRaygunModule with RaygunAccessory {
  val moduleKey = "SolarPanel"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.SolarPanel.segment"

  def createItem( id : Int ) = new ItemModule( id, this )
    .setUnlocalizedName("rayguns.SolarPanel")
    .setTextureName("rayguns:solar_panel")
    .setCreativeTab( ModularRayguns.raygunsTab )
    .setMaxStackSize(1)

  private[this] val entityMap = WeakHashMap[Entity, Boolean]()
  private[this] val random = new Random()

  override def handleTickEvent( event : GunTickEvent ) {
    if ( canSeeTheSun( event.world, event.player ) && event.world.getWorldTime() % 10 == 0 ) {
      event.components.battery.addCharge( event.gun, 1 )
    }
  }

  private def canSeeTheSun( world : World, entity : Entity )  : Boolean = {
    //translation of cpw's code for the solar helmet. All glory to cpw.
    if ( world.isRemote || world.provider.hasNoSky ) return false

    val x = Math.floor( entity.posX ).intValue()
    val y = Math.floor( entity.posY ).intValue()
    val z = Math.floor( entity.posZ ).intValue()

    if ( world.getTotalWorldTime() % 20 == 0 || !entityMap.contains( entity ) ) {
      val canRain = world.getWorldChunkManager().getBiomeGenAt(x, z).getIntRainfall() > 0
      entityMap.put( entity, canRain )
    }
    val canRain = entityMap( entity )
    val isRaining = canRain && ( world.isRaining() || world.isThundering() )

    !isRaining && world.isDaytime() && world.canBlockSeeTheSky(x, y, z)
  }
}