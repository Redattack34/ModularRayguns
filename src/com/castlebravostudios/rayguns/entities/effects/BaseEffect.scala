package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import java.util.Random

trait BaseEffect extends Entity {
  def colourRed : Float
  def colourBlue : Float
  def colourGreen : Float
  def colourAlpha : Float = 1.0f

  def hitEntity( entity : Entity ) : Unit
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit
  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit

  def collidesWithLiquids : Boolean = false

  def random : Random
}