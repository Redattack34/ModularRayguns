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

package com.castlebravostudios.rayguns.utils

import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.api.ShotRegistry
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunFrame
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.items.accessories.ChargeCapacitor
import com.castlebravostudios.rayguns.api.items.RaygunBarrel

case class GunComponents(frame : RaygunFrame, chamber : RaygunChamber, battery : RaygunBattery,
    barrel : RaygunBarrel, lens : Option[RaygunLens], accessory : Option[RaygunAccessory] ) {

  def components : Seq[RaygunModule] = Seq( frame, chamber, battery, barrel ) ++ lens ++ accessory

  //TODO: Ugly special-case hack. Find a better way to do this.
  def getFireEvent( charge : Double ) : FireEvent = accessory match {
    case Some( ChargeCapacitor ) => ChargeFireEvent( this, charge )
    case _ => DefaultFireEvent( this )
  }

  def isValid : Boolean = components.forall( c => c != null && ModuleRegistry.isRegistered(c) )
}

case class OptionalGunComponents(
  frame : Option[RaygunFrame], chamber : Option[RaygunChamber], battery : Option[RaygunBattery],
  barrel : Option[RaygunBarrel], lens : Option[RaygunLens], acc : Option[RaygunAccessory] ) {

  def components : Seq[RaygunModule] = frame.toSeq ++ chamber ++ battery ++ barrel ++ lens ++ acc
}
object OptionalGunComponents {
  def apply( comp : GunComponents ) : OptionalGunComponents =
    new OptionalGunComponents( Some( comp.frame ), Some( comp.chamber ),
        Some( comp.battery ), Some( comp.barrel ), comp.lens, comp.accessory );
}

