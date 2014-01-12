package com.castlebravostudios.rayguns.items;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/**
 * Bridge class to add type information to the strings list in addInformation,
 * since I can't implement a method that takes raw types in Scala.
 */
public abstract class ScalaItem extends Item {

  public ScalaItem( int id ) {
    super( id );
  }

  @SuppressWarnings( { "rawtypes", "unchecked", "cast" } )
  @Override
  public void addInformation( ItemStack item,
      EntityPlayer player, List strings, boolean advancedTooltips ) {
    super.addInformation( item, player, strings, advancedTooltips );
    scalaAddInformation( item, player, (List<String>)strings, advancedTooltips );
  }

  protected abstract void scalaAddInformation( ItemStack item, EntityPlayer player,
      List<String> strings, boolean advancedTooltips );

}
