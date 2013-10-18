package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemChamber

object ModuleRegistry {

  private[this] var registeredBodies = Map[String, ItemBody]()
  private[this] var registeredChambers = Map[String, ItemChamber]()
  private[this] var registeredBatteries = Map[String, ItemBattery]()
  private[this] var registeredLenses = Map[String, ItemLens]()
  private[this] var registeredAccessories = Map[String, ItemAccessory]()

  def registerModule( module: ItemModule ) : Unit = module match {
    case item : ItemBody => registeredBodies += ( item.moduleKey -> item )
    case item : ItemChamber => registeredChambers += ( item.moduleKey -> item )
    case item : ItemBattery => registeredBatteries += ( item.moduleKey -> item )
    case item : ItemLens => registeredLenses += ( item.moduleKey -> item )
    case item : ItemAccessory => registeredAccessories += ( item.moduleKey -> item )
  }

  def getBody( key : String ) : Option[ItemBody] = registeredBodies.get( key )
  def getChamber( key : String ) : Option[ItemChamber] = registeredChambers.get( key )
  def getBattery( key : String ) : Option[ItemBattery] = registeredBatteries.get( key )
  def getLens( key : String ) : Option[ItemLens] = registeredLenses.get( key )
  def getAccessory( key : String ) : Option[ItemAccessory] = registeredAccessories.get( key )
}