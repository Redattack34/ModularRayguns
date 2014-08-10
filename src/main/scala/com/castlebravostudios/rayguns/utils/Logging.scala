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

import com.castlebravostudios.rayguns.mod.ModularRayguns
import org.apache.logging.log4j.Level

trait Logging {

  private val logger = ModularRayguns.logger

  protected def error( message : => String ) =
    log( Level.ERROR, message )

  protected def warning( message : => String ) =
    log( Level.WARN, message )

  protected def info( message : => String ) =
    log( Level.INFO, message )

  protected def debug( message : => String ) =
    log( Level.DEBUG, message )

  protected def trace( message : => String ) =
    log( Level.TRACE, message )

  protected def error( message : => String, t : Throwable ) =
    log( Level.ERROR, message, t )

  protected def warning( message : => String, t : Throwable ) =
    log( Level.WARN, message, t )

  protected def info( message : => String, t : Throwable ) =
    log( Level.INFO, message, t )

  protected def debug( message : => String, t : Throwable ) =
    log( Level.DEBUG, message, t )

  protected def trace( message : => String, t : Throwable ) =
    log( Level.TRACE, message, t )

  private def log( level : Level, message : => String ) = {
    if ( logger.isEnabled(level) ) {
      logger.log( level, message )
    }
  }

  private def log( level : Level, message : => String, t : Throwable ) = {
    if ( logger.isEnabled(level) ) {
      logger.log( level, message, t )
    }
  }
}