package com.castlebravostudios.rayguns.utils

import com.castlebravostudios.rayguns.api.items._
import com.castlebravostudios.rayguns.api.ModuleRegistry

trait GunConfiguration {
  def components : Seq[ItemModule]
}
trait FiringConfiguration {
  def components : Seq[ItemModule]

  def powerMultiplier : Double = components.map(_.powerModifier).product

  def isValid : Boolean = components.forall( c => c != null && ModuleRegistry.isRegistered(c) )
}
case class GunComponents(body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], acc : Option[ItemAccessory] ) extends GunConfiguration with FiringConfiguration {

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ acc
}
case class ChargedGun( body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], acc : Option[ItemAccessory], charge : Double ) extends FiringConfiguration {

  override def powerMultiplier : Double = super.powerMultiplier * Math.pow( charge, 0.444444444d );

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ acc
}
case class OptionalGunComponents(
  body : Option[ItemBody], chamber : Option[ItemChamber], battery : Option[ItemBattery],
  lens : Option[ItemLens], acc : Option[ItemAccessory] ) extends GunConfiguration {

  def this( comp : GunComponents ) = this( Some( comp.body ),
      Some( comp.chamber ), Some( comp.battery ), comp.lens, comp.acc );

  def components = body.toSeq ++ chamber ++ battery ++ lens ++ acc
}