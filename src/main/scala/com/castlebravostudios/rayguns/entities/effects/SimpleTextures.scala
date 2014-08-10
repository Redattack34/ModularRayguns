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

package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.mod.ModularRayguns
import net.minecraft.util.ResourceLocation

trait SimpleTextures extends BaseEffect {

  def textureName : String

  private[this] val boltTex = ModularRayguns.texture( s"textures/bolts/bolt_${textureName}.png" )
  private[this] val beamGlowTex = ModularRayguns.texture( s"textures/beams/beam_glow_${textureName}.png" )
  private[this] val beamCoreTex = ModularRayguns.texture( s"textures/beams/beam_core_${textureName}.png" )
  private[this] val beamStartGlowTex = ModularRayguns.texture( s"textures/beams/beam_start_glow_${textureName}.png" )
  private[this] val beamStartCoreTex = ModularRayguns.texture( s"textures/beams/beam_start_core_${textureName}.png" )
  private[this] val beamNoiseTex = ModularRayguns.texture( s"textures/beams/beam_noise_${textureName}.png" )
  private[this] val chargeTex = ModularRayguns.texture( s"textures/effects/charge/${textureName}_charge.png" )

  def boltTexture : ResourceLocation = boltTex
  def beamGlowTexture : ResourceLocation = beamGlowTex
  def beamCoreTexture : ResourceLocation = beamCoreTex
  def beamStartGlowTexture : ResourceLocation = beamStartGlowTex
  def beamStartCoreTexture : ResourceLocation = beamStartCoreTex
  def beamNoiseTexture : ResourceLocation = beamNoiseTex
  def chargeTexture : ResourceLocation = chargeTex
}