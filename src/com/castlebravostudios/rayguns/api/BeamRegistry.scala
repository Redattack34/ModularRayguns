package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemFocus
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.utils.GunComponents
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.Entity

object BeamRegistry {

  type BeamCreator = (World, EntityPlayer) => Entity

  private var registrations = Seq[PartialFunction[GunComponents, BeamCreator]]()

  /**
   * Register a partial function which maps module combinations to beam creation
   * functions.
   */
  def register( f : PartialFunction[GunComponents, BeamCreator] ) : Unit =
    registrations = f +: registrations

  def isValid( m : GunComponents ) : Boolean =
    m.productIterator.forall( _ != null) &&
    registrations.exists( _.isDefinedAt( m ) )

  def getFunction( m : GunComponents ) : Option[BeamCreator] =
    registrations.find( _.isDefinedAt(m) ).map( _.apply(m) )
}