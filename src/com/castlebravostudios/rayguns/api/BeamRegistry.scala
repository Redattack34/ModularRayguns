package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunChamber
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