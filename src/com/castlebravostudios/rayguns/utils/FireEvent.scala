package com.castlebravostudios.rayguns.utils

import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.ItemBattery

trait FireEvent extends {
  def components : Seq[ItemModule]

  def powerMultiplier : Double = components.map(_.powerModifier).product

  def isValid : Boolean = BeamRegistry.isValid(this)
}
case class DefaultFireEvent(body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], accessory : Option[ItemAccessory] ) extends FireEvent {

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ accessory

  def this( comp : GunComponents ) = this( comp.body,
    comp.chamber, comp.battery, comp.lens, comp.accessory );
}
case class ChargeFireEvent( body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], accessory : Option[ItemAccessory], charge : Double ) extends FireEvent {

  def this( comp : GunComponents, charge : Double ) = this( comp.body,
    comp.chamber, comp.battery, comp.lens, comp.accessory, charge );

  override def powerMultiplier : Double = super.powerMultiplier * Math.pow( charge, 0.444444444d );

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ accessory
}