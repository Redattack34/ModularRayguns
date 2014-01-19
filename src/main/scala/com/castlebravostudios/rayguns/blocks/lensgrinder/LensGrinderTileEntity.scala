package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity
import com.castlebravostudios.rayguns.blocks.PoweredBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.nbt.NBTTagCompound
import com.castlebravostudios.rayguns.plugins.te.RFBlockPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2BlockPowerConnector

class LensGrinderTileEntity extends BaseInventoryTileEntity with PoweredBlock
  with RFBlockPowerConnector with IC2BlockPowerConnector {

  import LensGrinderTileEntity._

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
    if ( slot == OUTPUT_SLOT ) output else input.getStackInSlot( slot )
  override def setInventorySlotContents( slot : Int, stack : ItemStack ) = {
    if ( slot == OUTPUT_SLOT ) {
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
        this.onInventoryChanged()
      }
      else if (!this.canGrind()) {
        this.remainingTime = 0
        this.onInventoryChanged()
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
    for ( slot <- 0 until input.getSizeInventory() ) {
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
          output.itemID == recipeOutput.itemID &&
            output.stackSize + recipeOutput.stackSize <= output.getMaxStackSize
        }

      case None => false
    }
  }

  def isGrinding = remainingTime > 0
  def getTimeRemainingScaled( scale : Int ) : Int = {
    def totalTime : Int = this.recipe.get.ticks
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

  val getInventoryStackLimit = 64

  override def getInvName : String = "rayguns.lensGrinderEntity"
  override def isInvNameLocalized : Boolean = false

  override def isItemValidForSlot( slot : Int, item : ItemStack ) : Boolean =
    slot != OUTPUT_SLOT

  private class DummyContainer extends Container {
    def canInteractWith( player : EntityPlayer ) = false
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