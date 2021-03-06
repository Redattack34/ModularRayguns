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

import java.util.Random

import com.castlebravostudios.rayguns.api.ShotModifier

import com.castlebravostudios.rayguns.api.ShotRegistry
import com.castlebravostudios.rayguns.api.items.BaseRaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.items.accessories.ChargeCapacitor
import com.castlebravostudios.rayguns.items.barrels.BeamBarrel
import com.castlebravostudios.rayguns.items.barrels.BlasterBarrel
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.utils.ChargeFireEvent
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.Vector3

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World


abstract class BaseChamber extends BaseRaygunModule with RaygunChamber {

  val rand = new Random()

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
    ShotRegistry.registerModifier( ShotModifier{ case ev : ChargeFireEvent => ev.toDefault }{
      case ChargeFireEvent(_, ch, _, _, _, Some(ChargeCapacitor), charge) if (ch eq this) => { (f) =>
        f().map{ shot => shot.charge *= charge; shot }
      }
    })
  }

  def registerScatterShotHandler( ) : Unit = {
    def getClampedGaussian() : Float =
      MathHelper.clamp_float(-2.0f, rand.nextGaussian().floatValue, 2.0f)
    def scatter( vec : Vector3, factor : Float ) : Vector3 =
      vec.modify( _ + (getClampedGaussian() * factor) ).normalized

    ShotRegistry.registerModifier( ShotModifier{ case ev : DefaultFireEvent => ev.copy( lens = None ) }{
      case DefaultFireEvent(_, ch, _, _, Some(WideLens), _ ) if ( ch eq this ) => { (f) =>
        Seq.fill(9)( f() ).flatten.map{ shot =>
          shot.aimVector = scatter( shot.aimVector, 0.1f )
          shot.charge = 0.5
          shot
        }
      }
    })
  }

  def registerPreciseShotHandler( ) : Unit = {
    ShotRegistry.registerModifier( ShotModifier{ case ev : DefaultFireEvent => ev.copy( lens = None ) }{
      case DefaultFireEvent(_, ch, _, BlasterBarrel, Some(PreciseLens), _) if (ch eq this) => { (f) =>
        f().map{ shot =>
          shot.asInstanceOf[BaseBoltEntity].depletionRate = 0.025d
          shot
        }
      }
      case DefaultFireEvent(_, ch, _, BeamBarrel, Some(PreciseLens), _) if (ch eq this) => { (f) =>
        f().map{ shot =>
          shot.asInstanceOf[BaseBeamEntity].maxRange = 40
          shot
        }
      }
    })
  }

  def registerSingleShotHandlers( ) : Unit = {
    ShotRegistry.registerCreator({
      case DefaultFireEvent(_, ch, _, BlasterBarrel, _, _) if ch eq this => { (world, player) =>
        Seq( createAndInitBolt( world, player ) )
      }
      case DefaultFireEvent(_, ch, _, BeamBarrel, _, _) if ch eq this => { (world, player) =>
        Seq( createAndInitBeam( world, player ) )
      }
    })
  }

  def chargeTexture : ResourceLocation = shotEffect.chargeTexture
}