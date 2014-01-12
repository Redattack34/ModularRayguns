package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunChamber

object ModuleRegistry {

  private[this] var registeredBodies = Map[String, RaygunBody]()
  private[this] var registeredChambers = Map[String, RaygunChamber]()
  private[this] var registeredBatteries = Map[String, RaygunBattery]()
  private[this] var registeredLenses = Map[String, RaygunLens]()
  private[this] var registeredAccessories = Map[String, RaygunAccessory]()

  def registerModule( module: RaygunModule ) : Unit = module match {
    case item : RaygunBody => registeredBodies += ( item.moduleKey -> item )
    case item : RaygunChamber => registeredChambers += ( item.moduleKey -> item )
    case item : RaygunBattery => registeredBatteries += ( item.moduleKey -> item )
    case item : RaygunLens => registeredLenses += ( item.moduleKey -> item )
    case item : RaygunAccessory => registeredAccessories += ( item.moduleKey -> item )
  }

  def getBody( key : String ) : Option[RaygunBody] = registeredBodies.get( key )
  def getChamber( key : String ) : Option[RaygunChamber] = registeredChambers.get( key )
  def getBattery( key : String ) : Option[RaygunBattery] = registeredBatteries.get( key )
  def getLens( key : String ) : Option[RaygunLens] = registeredLenses.get( key )
  def getAccessory( key : String ) : Option[RaygunAccessory] = registeredAccessories.get( key )

  def isRegistered( module : Any ) : Boolean = module match {
    case item : RaygunBody => registeredBodies.contains(item.moduleKey)
    case item : RaygunChamber => registeredChambers.contains(item.moduleKey)
    case item : RaygunBattery => registeredBatteries.contains(item.moduleKey)
    case item : RaygunLens => registeredLenses.contains(item.moduleKey)
    case item : RaygunAccessory => registeredAccessories.contains(item.moduleKey)
    case _ => false
  }
}