package com.castlebravostudios.rayguns.utils

import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunBattery

trait FireEvent extends {
  def components : Seq[RaygunModule]

  def powerMultiplier : Double = components.map(_.powerModifier).product

  def isValid : Boolean = BeamRegistry.isValid(this)
}
case class DefaultFireEvent(body : RaygunBody, chamber : RaygunChamber, battery : RaygunBattery,
    lens : Option[RaygunLens], accessory : Option[RaygunAccessory] ) extends FireEvent {

  def components : Seq[RaygunModule] = Seq( body, chamber, battery ) ++ lens ++ accessory

  def this( comp : GunComponents ) = this( comp.body,
    comp.chamber, comp.battery, comp.lens, comp.accessory );
}
case class ChargeFireEvent( body : RaygunBody, chamber : RaygunChamber, battery : RaygunBattery,
    lens : Option[RaygunLens], accessory : Option[RaygunAccessory], charge : Double ) extends FireEvent {

  def this( comp : GunComponents, charge : Double ) = this( comp.body,
    comp.chamber, comp.battery, comp.lens, comp.accessory, charge );

  override def powerMultiplier : Double = super.powerMultiplier * Math.pow( charge, 0.444444444d );

  def components : Seq[RaygunModule] = Seq( body, chamber, battery ) ++ lens ++ accessory
}