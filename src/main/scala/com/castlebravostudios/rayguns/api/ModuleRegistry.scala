/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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