package com.castlebravostudios.rayguns.blocks.lensgrinder

import com.castlebravostudios.rayguns.blocks.BaseInventoryTileEntity
import net.minecraft.item.ItemStack
import scala.collection.mutable.ListBuffer
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.inventory.Container
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import net.minecraft.item.crafting.ShapedRecipes
import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import net.minecraft.nbt.NBTTagCompound

class LensGrinderTileEntity extends BaseInventoryTileEntity {

  import LensGrinderTileEntity._

  private[this] val input = new InventoryCrafting( new DummyContainer(), 3, 3 )
  private[this] var output : ItemStack = null

  private[this] var remainingTime = 0

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

  private def resetRecipe() : Unit = {
    val recipeOpt = getRecipe()
    if ( canGrind( recipeOpt ) ) {
      remainingTime = recipeOpt.get.ticks
    }
    else {
      remainingTime = 0
    }
  }

  override def updateEntity() : Unit = {
    if (this.remainingTime > 0) {

      remainingTime -= 1

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
      resetRecipe()
    }

    super.updateEntity()
  }

  private def completeGrinding() : Unit = {
    val recipe = getRecipe().get.recipe

    subtractInput( recipe )
    addOutput( recipe )

    resetRecipe()
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
    canGrind( getRecipe() )
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
    def totalTime : Int = getRecipe().get.ticks
    val factor = (totalTime - remainingTime).toDouble / totalTime.toDouble
    (factor * scale).toInt
  }

  override def readFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readFromNBT(tag)
    remainingTime = tag.getShort("RemainingTime")
  }

  override def writeToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeToNBT(tag)
    tag.setShort( "RemainingTime", remainingTime.shortValue )
  }

  private def getRecipe() : Option[LensGrinderRecipe] =
    LensGrinderRecipeRegistry.getRecipe( input )

  val getInventoryStackLimit = 64

  override def getInvName : String = "rayguns.lensGrinderEntity"
  override def isInvNameLocalized : Boolean = false

  override def isItemValidForSlot( slot : Int, item : ItemStack ) : Boolean =
    slot != OUTPUT_SLOT

  private class DummyContainer extends Container {
    def canInteractWith( player : EntityPlayer ) = false
    override def onCraftMatrixChanged( inv : IInventory ) : Unit = {
      super.onCraftMatrixChanged(inv)
      resetRecipe
    }
  }
}
object LensGrinderTileEntity {

  val OUTPUT_SLOT = 9
}