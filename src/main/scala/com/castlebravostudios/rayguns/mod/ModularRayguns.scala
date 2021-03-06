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

package com.castlebravostudios.rayguns.mod

import com.castlebravostudios.rayguns.blocks.TileEntities
import com.castlebravostudios.rayguns.entities.Entities
import com.castlebravostudios.rayguns.entities.effects.Effects
import com.castlebravostudios.rayguns.utils.Extensions.ItemExtensions
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.network.NetworkRegistry
import org.apache.logging.log4j.Logger
import com.castlebravostudios.rayguns.items.RaygunsItems
import com.castlebravostudios.rayguns.items.RaygunsBlocks
import com.castlebravostudios.rayguns.items.frames.FireflyFrame
import net.minecraft.item.Item
import cpw.mods.fml.common.event.FMLInterModComms
import net.minecraft.nbt.NBTTagCompound

@Mod(modid="mod_ModularRayguns", version="1.0-alpha2", modLanguage="scala", useMetadata=true)
object ModularRayguns {

  private var _logger : Logger = _
  def logger : Logger = _logger

  @SidedProxy(clientSide="com.castlebravostudios.rayguns.mod.ClientProxy",
      serverSide="com.castlebravostudios.rayguns.mod.CommonProxy")
  var proxy : CommonProxy = null

  @EventHandler
  def preInit( event : FMLPreInitializationEvent ) : Unit = {
    _logger = event.getModLog()
    Config.load( event.getSuggestedConfigurationFile() )
  }

  @EventHandler
  def postInit( event : FMLPostInitializationEvent ) : Unit = Unit

  @EventHandler
  def load( event : FMLInitializationEvent ) : Unit = {
    RaygunsItems.registerItems
    RaygunsBlocks.registerBlocks
    Entities.registerEntities
    TileEntities.registerTileEntities
    Effects.registerEffects
    NetworkRegistry.INSTANCE.registerGuiHandler(ModularRayguns, proxy)

    Config.recipeLibrary.registerRecipes()

    proxy.registerRenderers()
    proxy.loadTextures()

    FMLInterModComms.sendMessage("Waila", "register",
        "com.castlebravostudios.rayguns.plugins.waila.RaygunsWailaModule.register" )

    val guideMessage = new NBTTagCompound
    guideMessage.setString("name", "Modular Rayguns")
    guideMessage.setString("location", "rayguns:doc/ModularRayguns.md")

    FMLInterModComms.sendMessage("mod_TheGuide", "RegisterIndexFile",
        guideMessage )
  }

  val raygunsTab  = new CreativeTabs("tabRayguns") {
    override def getTabIconItem : Item =
      FireflyFrame.item.get
  }

  def texture( path : String ) : ResourceLocation =
    new ResourceLocation( "rayguns", path )
}
