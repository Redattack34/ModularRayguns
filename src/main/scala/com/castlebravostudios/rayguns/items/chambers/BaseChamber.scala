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

package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.ShotRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.items.accessories.ChargeCapacitor
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import com.castlebravostudios.rayguns.utils.Vector3


abstract class BaseChamber extends BaseRaygunModule with RaygunChamber {

  def shotEffect : BaseEffect

  def initBolt( world : World, player : EntityPlayer, bolt : BaseBoltEntity ) : Unit = initShot( world, player, bolt )
  def initBeam( world : World, player : EntityPlayer, beam : BaseBeamEntity ) : Unit = initShot( world, player, beam )
  def initShot( world : World, player : EntityPlayer, shot : Shootable ) : Unit = ()

  def createAndInitBolt( world : World, player : EntityPlayer ) : BaseBoltEntity = {
    val bolt = shotEffect.createBoltEntity(world, player)
    bolt.aimVector = Vector3( player.getLookVec() )
    initBolt( world, player, bolt )
    bolt
  }

  def createAndInitBeam( world : World, player : EntityPlayer ) : BaseBeamEntity = {
    val beam = shotEffect.createBeamEntity(world, player)
    beam.aimVector = Vector3( player.getLookVec() )
    initBeam(world, player, beam)
    beam
  }

  def registerChargedShotHandler( ) : Unit = {
    def toDefault( ev : ChargeFireEvent ) : DefaultFireEvent =
      new DefaultFireEvent( ev.body, ev.chamber, ev.battery, ev.lens, ev.accessory )
    def canCharge( ev : ChargeFireEvent ) : Boolean =
      ShotRegistry.hasShotCreator( toDefault( ev ) )

    ShotRegistry.registerModifier({
      case ev@ChargeFireEvent(_, ch, _, _, Some(ChargeCapacitor), charge) if (ch eq this) && canCharge( ev ) => { (world, player) =>
        val newEvent = toDefault( ev )
        val creator = ShotRegistry.getShotCreator( newEvent ).get
        val seq = creator( newEvent )( world, player )

        val adjustedCharge = if ( charge < 1 ) charge
          else ( ( charge - 1 ) / seq.size ) + 1

        seq.foreach( _.charge *= adjustedCharge )
        seq
      }
    })
  }

  def registerScatterShotHandler( ) : Unit = {
/*    ShotRegistry.register({
      case DefaultFireEvent(_, ch, _, Some(WideLens), _ ) if ch eq this => { (world, player) =>
        BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
          createAndInitBolt(world, player )
        }
      }
    })*/
  }

  def registerSingleShotHandlers( ) : Unit = {
    ShotRegistry.registerCreator({
      case DefaultFireEvent(_, ch, _, None, _) if ch eq this => { (world, player) =>
        Seq( createAndInitBolt( world, player ) )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseLens), _ ) if ch eq this => { (world, player) =>
        val bolt = createAndInitBolt(world, player)
        bolt.depletionRate =  0.025d
        Seq( bolt )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseBeamLens), _ ) if ch eq this => { (world, player) =>
        Seq( createAndInitBeam(world, player) )
      }
    })
  }

  def chargeTexture : ResourceLocation = shotEffect.chargeTexture
}