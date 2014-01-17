package com.castlebravostudios.rayguns.blocks

trait PoweredBlock {

  def chargeCapacity : Int
  def chargeStored : Int
  def maxChargeInput : Int
  def addCharge( charge : Int ) : Unit
}