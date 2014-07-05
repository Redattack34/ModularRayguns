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

package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity
import com.castlebravostudios.rayguns.blocks.PoweredBlock
import com.castlebravostudios.rayguns.plugins.te.RFBlockPowerConnector

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity

class LensGrinderTileEntity extends BaseInventoryTileEntity with PoweredBlock
  with RFBlockPowerConnector {


  private[this] val input = new InventoryCrafting( new DummyContainer(), 3, 3 )
  private[this] var output : ItemStack = null

  private[this] var remainingTime = 0

  private[this] var recipe : Option[LensGrinderRecipe] = None

  var chargeStored : Int = 0
  val chargeCapacity : Int = 10
  val maxChargeInput : Int = 4
  private[this] val chargeUsagePerTick : Int = 2

  override def getSizeInventory : Int = 10
  override def getStackInSlot( slot : Int ) : ItemStack =
    if ( slot == LensGrinderTileEntity.OUTPUT_SLOT ) output else input.getStackInSlot( slot )
  override def setInventorySlotContents( slot : Int, stack : ItemStack ) : Unit = {
    if ( slot == LensGrinderTileEntity.OUTPUT_SLOT ) {
      output = stack
      if ( stack != null && stack.stackSize > getInventoryStackLimit ) {
        stack.stackSize = getInventoryStackLimit
      }
    }
    else input.setInventorySlotContents( slot, stack )
  }

  def onPickedUpFrom( slot : Int ) : Unit = Unit
  def onSlotChanged( slot : Int ) : Unit = Unit

  private def updateRecipe() : Unit = {
    val recipeOpt = LensGrinderRecipeRegistry.getRecipe( input )

    if ( recipeOpt == this.recipe ) {
      return;
    }
    if ( canGrind( recipeOpt ) ) {
      this.recipe = recipeOpt
      remainingTime = recipeOpt.get.ticks
    }
    else {
      this.recipe = None
      remainingTime = 0
    }
  }

  override def updateEntity() : Unit = {
    if ( this.remainingTime > 0 && chargeStored >= chargeUsagePerTick ) {

      remainingTime -= 1
      chargeStored -= chargeUsagePerTick

      if (this.remainingTime == 0) {
        this.completeGrinding()
        this.markDirty()
      }
      else if (!this.canGrind()) {
        this.remainingTime = 0
        this.markDirty()
      }

      if ( remainingTime % 20 == 0 ) {
        worldObj.markBlockForUpdate( xCoord, yCoord, zCoord )
      }
    }
    else {
      updateRecipe()
    }

    super.updateEntity()
  }

  private def completeGrinding() : Unit = {
    val recipe = this.recipe.get.recipe

    subtractInput( recipe )
    addOutput( recipe )

    this.recipe = None
    updateRecipe()
  }

  private def subtractInput( recipe : ShapedRecipes ) : Unit = {
    for { slot <- 0 until input.getSizeInventory() } {
      input.decrStackSize(slot, 1)
    }
  }

  private def addOutput( recipe : ShapedRecipes ) : Unit = {
    val result = recipe.getRecipeOutput()
    if ( output == null ) output = result
    else output.stackSize += result.stackSize
  }

  private def canGrind( ) : Boolean =
    canGrind( this.recipe )
  private def canGrind( recipe : Option[LensGrinderRecipe] ) : Boolean = {
    recipe match {
      case Some( r ) =>
        if ( output == null ) true
        else {
          val recipeOutput = r.recipe.getRecipeOutput
          output == recipeOutput &&
            output.stackSize + recipeOutput.stackSize <= output.getMaxStackSize
        }

      case None => false
    }
  }

  def isGrinding : Boolean = remainingTime > 0
  def getTimeRemainingScaled( scale : Int ) : Int = {
    val totalTime : Int = this.recipe.get.ticks
    val factor = (totalTime - remainingTime).toDouble / totalTime.toDouble
    (factor * scale).toInt
  }

  override def readFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readFromNBT(tag)
    remainingTime = tag.getShort("RemainingTime")
    chargeStored = tag.getShort("ChargeStored");
  }

  override def writeToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeToNBT(tag)
    tag.setShort( "RemainingTime", remainingTime.shortValue )
    tag.setShort( "ChargeStored", chargeStored.shortValue )
  }

  override def getDescriptionPacket() : Packet = {
    val tag = new NBTTagCompound
    writeToNBT(tag)
    new S35PacketUpdateTileEntity( xCoord, yCoord, zCoord, 0, tag )
  }

  override def onDataPacket( net : NetworkManager, packet : S35PacketUpdateTileEntity ) : Unit = {
    readFromNBT( packet.func_148857_g() )
  }

  val getInventoryStackLimit = 64

  override def getInventoryName : String = "rayguns.lensGrinderEntity"
  override def hasCustomInventoryName : Boolean = false

  override def isItemValidForSlot( slot : Int, item : ItemStack ) : Boolean =
    slot != LensGrinderTileEntity.OUTPUT_SLOT

  private class DummyContainer extends Container {
    def canInteractWith( player : EntityPlayer ) : Boolean = false
    override def onCraftMatrixChanged( inv : IInventory ) : Unit = {
      super.onCraftMatrixChanged(inv)
      updateRecipe
    }
  }

  def addCharge( charge : Int ) : Unit = chargeStored += charge;
}
object LensGrinderTileEntity {

  val OUTPUT_SLOT = 9
}