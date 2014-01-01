package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.utils.FireEvent

object BeamRegistry {

  type BeamCreator = (World, EntityPlayer) => Unit

  private var registrations = Seq[PartialFunction[FireEvent, BeamCreator]]()

  /**
   * Register a partial function which maps module combinations to beam creation
   * functions.
   */
  def register( f : PartialFunction[FireEvent, BeamCreator] ) : Unit =
    registrations = f +: registrations

  def isValid( m : FireEvent ) : Boolean =
    registrations.exists( _.isDefinedAt( m ) )

  def getFunction( m : FireEvent ) : Option[BeamCreator] =
    registrations.find( _.isDefinedAt(m) ).map( _.apply(m) )
}