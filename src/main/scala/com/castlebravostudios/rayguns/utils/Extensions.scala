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

import net.minecraft.world.World
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.Item
import net.minecraft.block.Block

object Extensions extends Logging {

  implicit class WorldExtension(val world : World) extends AnyVal {
    def isOnClient : Boolean = world.isRemote
    def isOnServer : Boolean = !world.isRemote

    def debug(name : String, value : Any) : Unit = {
      if ( isOnClient ) info( s"Client $name: $value" )
      if ( isOnServer ) info( s"Server $name: $value" )
    }
  }

  implicit class ItemStackExtension( val item : ItemStack ) extends AnyVal {

    def getTagCompoundSafe : NBTTagCompound = {
      if ( item.getTagCompound() == null ) {
        item.setTagCompound( new NBTTagCompound() )
      }
      item.getTagCompound()
    }

  }

  implicit class ItemExtensions( val item : Item ) extends AnyVal {

    def asStack : ItemStack = new ItemStack( item )
    def asStack( count : Int ) : ItemStack = new ItemStack( item, count )
    def asStack( count : Int, meta : Int ) : ItemStack = new ItemStack( item, count, meta )
  }

  implicit class BlockExtensions( val block : Block ) extends AnyVal {

    def asStack : ItemStack = new ItemStack( block )
    def asStack( count : Int ) : ItemStack = new ItemStack( block, count )
    def asStack( count : Int, meta : Int ) : ItemStack = new ItemStack( block, count, meta )
  }
}