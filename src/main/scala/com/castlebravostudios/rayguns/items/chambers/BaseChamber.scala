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

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
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
import com.castlebravostudios.rayguns.items.accessories.ChargeCapacitor


abstract class BaseChamber extends BaseRaygunModule with RaygunChamber {

  def shotEffect : BaseEffect

  def initBolt( world : World, player : EntityPlayer, bolt : BaseBoltEntity ) : Unit = initShot( world, player, bolt )
  def initBeam( world : World, player : EntityPlayer, beam : BaseBeamEntity ) : Unit = initShot( world, player, beam )
  def initShot( world : World, player : EntityPlayer, shot : Shootable ) : Unit = ()

  def createAndInitBolt( world : World, player : EntityPlayer ) : BaseBoltEntity = {
    val bolt = shotEffect.createBoltEntity(world, player)
    initBolt( world, player, bolt )
    bolt
  }

  def createAndInitBeam( world : World, player : EntityPlayer ) : BaseBeamEntity = {
    val beam = shotEffect.createBeamEntity(world, player)
    initBeam(world, player, beam)
    beam
  }

  def registerChargedShotHandler( ) : Unit = {
    BeamRegistry.register({
      case ChargeFireEvent(_, ch, _, Some(ChargeLens), _, charge ) if ch eq this => { (world, player) =>
        val bolt = createAndInitBolt( world, player )
        bolt.charge = charge
        BoltUtils.spawnNormal( world, bolt, player )
      }
      case ChargeFireEvent(_, ch, _, Some(ChargeBeamLens), _, charge ) if ch eq this => { (world, player) =>
        val beam = createAndInitBeam( world, player )
        beam.charge = charge
        BeamUtils.spawnSingleShot( beam, world, player )
      }
      case ChargeFireEvent(_, ch, _, None, Some(ChargeCapacitor), charge) if ch eq this => { (world, player) =>
        val bolt = createAndInitBolt( world, player )
        bolt.charge = charge
        BoltUtils.spawnNormal( world, bolt, player )
      }
      case ChargeFireEvent(_, ch, _, Some(PreciseLens), Some(ChargeCapacitor), charge ) if ch eq this => { (world, player) =>
        val bolt = createAndInitBolt( world, player )
        bolt.charge = charge
        BoltUtils.spawnNormal( world, bolt, player )
      }
      case ChargeFireEvent(_, ch, _, Some(PreciseBeamLens), Some(ChargeCapacitor), charge ) if ch eq this => { (world, player) =>
        val beam = createAndInitBeam( world, player )
        beam.charge = charge
        BeamUtils.spawnSingleShot( beam, world, player )
      }
    })
  }

  def registerScatterShotHandler( ) : Unit = {
    BeamRegistry.register({
      case DefaultFireEvent(_, ch, _, Some(WideLens), _ ) if ch eq this => { (world, player) =>
        BoltUtils.spawnScatter(world, player, 9, 0.1f ){ () =>
          createAndInitBolt(world, player )
        }
      }
    })
  }

  def registerSingleShotHandlers( ) : Unit = {
    BeamRegistry.register({
      case DefaultFireEvent(_, ch, _, None, _) if ch eq this => { (world, player) =>
        BoltUtils.spawnNormal( world, createAndInitBolt( world, player ), player )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseLens), _ ) if ch eq this => { (world, player) =>
        BoltUtils.spawnPrecise( world, createAndInitBolt(world, player), player )
      }
      case DefaultFireEvent(_, ch, _, Some(PreciseBeamLens), _ ) if ch eq this => { (world, player) =>
        BeamUtils.spawnSingleShot( createAndInitBeam(world, player), world, player )
      }
    })
  }

  def chargeTexture : ResourceLocation = shotEffect.chargeTexture
}