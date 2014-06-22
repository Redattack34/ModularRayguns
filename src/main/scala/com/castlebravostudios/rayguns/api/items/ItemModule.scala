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
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.items.ScalaItem
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer

class ItemModule( val module : RaygunModule ) extends ScalaItem {

  //Override the setters to return ItemModule for easier chaining in the modules.
  override def setContainerItem( item : Item ) : ItemModule = { super.setContainerItem(item); this }
  override def setCreativeTab( tab : CreativeTabs ) : ItemModule = { super.setCreativeTab(tab); this }
  override def setFull3D() : ItemModule = { super.setFull3D(); this }
  override def setHasSubtypes( hasSubtypes : Boolean ) : ItemModule = { super.setHasSubtypes(hasSubtypes); this }
  override def setMaxDamage( maxDamage : Int ) : ItemModule = { super.setMaxDamage(maxDamage); this }
  override def setMaxStackSize( stackSize : Int ) : ItemModule = { super.setMaxStackSize(stackSize); this }
  override def setNoRepair() : ItemModule = { super.setNoRepair(); this }
  override def setPotionEffect( effect : String ) : ItemModule = { super.setPotionEffect(effect); this }
  override def setTextureName( texture : String ) : ItemModule = { super.setTextureName(texture); this }
  override def setUnlocalizedName( name : String ) : ItemModule = { super.setUnlocalizedName(name); this }

  override def scalaAddInformation( item : ItemStack, player : EntityPlayer,
    strings : java.util.List[String], advancedTooltips : Boolean ) : Unit = ()
}