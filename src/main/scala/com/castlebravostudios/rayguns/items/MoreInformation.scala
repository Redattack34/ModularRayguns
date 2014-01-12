package com.castlebravostudios.rayguns.items

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.FMLCommonHandler
import org.lwjgl.input.Keyboard
import net.minecraft.client.resources.I18n

trait MoreInformation extends ScalaItem {

  override def scalaAddInformation(item: ItemStack, player : EntityPlayer,
      strings: java.util.List[String], advancedTooltips : Boolean ) : Unit = {

    if ( showMoreInformation ) {
      getAdditionalInfo(item, player).foreach( strings.add(_) )
    }
    else {
      strings.add( I18n.getString("rayguns.moreinfo") )
    }
  }

  def getAdditionalInfo( item : ItemStack, player : EntityPlayer ) : Iterable[String]

  private def showMoreInformation : Boolean =
    FMLCommonHandler.instance().getEffectiveSide().isClient() &&
    ( Keyboard.isKeyDown( Keyboard.KEY_LSHIFT ) || Keyboard.isKeyDown( Keyboard.KEY_RSHIFT ) )
}