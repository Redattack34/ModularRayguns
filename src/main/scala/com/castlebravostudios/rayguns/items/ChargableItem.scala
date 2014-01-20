package com.castlebravostudios.rayguns.items

import net.minecraft.item.ItemStack

trait ChargableItem {

  def getChargeCapacity( item : ItemStack ) : Int
  def getChargeDepleted( item : ItemStack ) : Int
  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit
  def addCharge( item : ItemStack, delta : Int ) : Unit
  def getMaxChargePerTick( item : ItemStack ) : Int
}