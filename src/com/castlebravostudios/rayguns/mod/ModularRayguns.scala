package com.castlebravostudios.rayguns.mod

import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod.PreInit
import cpw.mods.fml.common.Mod.PostInit
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.Mod.Init
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.items.Blocks
import cpw.mods.fml.common.Mod.EventHandler
import com.castlebravostudios.rayguns.entities.Entities
import com.castlebravostudios.rayguns.blocks.TileEntities
import cpw.mods.fml.common.registry.LanguageRegistry
import net.minecraft.creativetab.CreativeTabs
import com.castlebravostudios.rayguns.items.bodies.MantisBody
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.items.bodies.FireflyBody
import com.castlebravostudios.rayguns.entities.effects.Effects

@Mod(modid="ModularRayguns", name="ModularRayguns", version="0.0.0", modLanguage="scala")
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
object ModularRayguns {

  @SidedProxy(clientSide="com.castlebravostudios.rayguns.mod.ClientProxy",
      serverSide="com.castlebravostudios.rayguns.mod.CommonProxy")
  var proxy : CommonProxy = null

  @EventHandler
  def preInit( event : FMLPreInitializationEvent ) : Unit =
    Config.load( event.getSuggestedConfigurationFile() )

  @EventHandler
  def postInit( event : FMLPostInitializationEvent ) : Unit = Unit

  @EventHandler
  def load( event : FMLInitializationEvent ) : Unit = {
    Items.registerItems
    Blocks.registerBlocks
    Entities.registerEntities
    TileEntities.registerTileEntities
    Effects.registerEffects

    proxy.registerRenderers()
    proxy.loadTextures()
  }

  val raygunsTab  = new CreativeTabs("tabRayguns") {
    override def getIconItemStack : ItemStack = new ItemStack( FireflyBody, 1, 0 )
  }
}
