package com.castlebravostudios.rayguns.utils

import com.castlebravostudios.rayguns.api.items._
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens

case class GunComponents(body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], accessory : Option[ItemAccessory] ) {

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ accessory

  def getFireEvent( charge : Double ) : FireEvent = lens match {
    case Some( ChargeLens ) => new ChargeFireEvent( this, charge )
    case Some( ChargeBeamLens ) => new ChargeFireEvent( this, charge )
    case _ => new DefaultFireEvent( this )
  }

  def isValid : Boolean = components.forall( c => c != null && ModuleRegistry.isRegistered(c) )
}
case class OptionalGunComponents(
  body : Option[ItemBody], chamber : Option[ItemChamber], battery : Option[ItemBattery],
  lens : Option[ItemLens], acc : Option[ItemAccessory] ) {

  def this( comp : GunComponents ) = this( Some( comp.body ),
      Some( comp.chamber ), Some( comp.battery ), comp.lens, comp.accessory );
}


