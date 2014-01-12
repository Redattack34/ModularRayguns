package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.entity.Entity

trait TriggerOnDeath extends BaseEffect {

  def hitEntity( shootable : Shootable, entity : Entity ) = true
  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) = true

  def triggerAt( shootable : Shootable, x : Double, y : Double, z : Double )
}