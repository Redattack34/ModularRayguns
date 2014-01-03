package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.entities.effects.BaseEffect

object EffectRegistry {

  private var registrations = Map[String, BaseEffect]()

  def registerEffect( effect : BaseEffect ) : Unit =
    registrations += ( effect.effectKey -> effect )

  def getEffect( key : String ) : Option[BaseEffect] = registrations.get( key )
}