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

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.FireEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.utils.BeamUtils
import com.castlebravostudios.rayguns.utils.Logging



/**
 * Registry for the objects that turn a mouse click with raygun in hand into a
 * shot.<p>
 *
 * There are three ways to hook into the shot creation using BeamRegistry.
 *  - Shot Creators take a FireEvent and produce a sequence of Shootables.
 *  - Shot Modifiers are like ShotCreators, but they always take precedence over
 *  shot creators, so they're useful for hooks that modify the results of a
 *  ShotCreator. Modifiers don't have to be created using ShotModifier, but it
 *  is helpful.
 *  - Shot Handlers take a FireEvent, world and player, and cause something to
 *  happen. One is preregistered that uses the creators and modifiers and
 *  spawns the resulting shots into the world.
 *
 * Generally, hooks that are registered later
 *
 * All of the hooks are in the form of PartialFunctions, so you should implement
 * isDefinedAt to ignore all fire events and so on that you don't want to handle.
 * Scala has special syntax for this, similar to pattern matching, so you don't
 * have to - see BaseChamber for a Scala example.
 */

object ShotRegistry extends Logging {

  type ShotCreator = PartialFunction[FireEvent, (World, EntityPlayer) => Seq[Shootable]]
  type ShotHandler = PartialFunction[FireEvent, (World, EntityPlayer) => Unit]

  private var creators = Seq[ShotCreator]()
  private var modifiers = Seq[ShotCreator]()
  private var handlers = Seq[ShotHandler]()

  def registerCreator( c : ShotCreator ) : Unit =
    creators = c +: creators

  def registerModifier( m : ShotCreator ) : Unit =
    modifiers = m +: modifiers

  def registerHandler( h : ShotHandler ) : Unit =
    handlers = h +: handlers

  /**
   * Returns true if there is a shot modifier or shot creator that can handle
   * the given event.
   */
  def hasShotCreator( event :  FireEvent ) = getShotCreator( event ).isDefined

  /**
   * Get the shot modifier or shot creator that can handle the given event,
   * or None if none has been registered. This can be used in shot modifiers
   * and shot handlers to get a shot creator for a given event, though care
   * should be taken to ensure that you don't get back the hook you're calling
   * from (or another which might call yours).
   */
  def getShotCreator( event : FireEvent ) : Option[ShotCreator] =
    modifiers.find( _.isDefinedAt( event ) )
      .orElse( creators.find( _.isDefinedAt( event ) ) )

  /**
   * Returns true if there is a shot handler that can handle the given event.
   * Not to be confused with hasShotCreator.
   */
  def isValid( event : FireEvent ) = getShotHandler( event ).isDefined

  def getShotHandler( event : FireEvent ) : Option[ShotHandler] =
    handlers.find( _.isDefinedAt( event) )

  def getFunction( event : FireEvent ) : Option[(World, EntityPlayer) => Unit] =
    getShotHandler( event ).map( _.apply( event ) )

  registerHandler({
    case ( event ) if ( hasShotCreator( event ) ) => { ( world, player ) =>
      val shotCreator = getShotCreator( event ).get

      shotCreator.apply( event ).apply( world, player ).foreach { shot => shot match {
          case bolt : BaseBoltEntity => BoltUtils.spawn( world, player, bolt )
          case beam : BaseBeamEntity => BeamUtils.spawn( world, player, beam )
          case _ => {
            severe( s"Unknown shot type ($shot, $event) - register your own handler." )
          }
        }
      }
    }
  })

}