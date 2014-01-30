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

import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.nbt.NBTTagCompound
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

object MatterTransporterEffect extends BaseEffect {

  val effectKey: String = "MatterTransporterEffect"

  def hitBlock( shootable: Shootable, hitX: Int, hitY: Int, hitZ: Int, side: Int ) : Boolean = {
    val itemId = shootable.asInstanceOf[MatterTransporterShootable].itemId
    if ( itemId == 0 ) return true

    val item = Item.itemsList(itemId)
    item.onItemUse( new ItemStack( item, 1 ), shootable.shooter.asInstanceOf[EntityPlayer], shootable.worldObj,
        hitX, hitY, hitZ, side, hitX + 0.5f, hitY + 0.5f, hitZ + 0.5f)
  }

  def hitEntity( shootable: Shootable, entity: Entity ): Boolean = false

  override def createBeamEntity( world : World, player : EntityPlayer ) : MatterTransporterBeamEntity = {
    val beam = new MatterTransporterBeamEntity( world )
    beam.itemId = deductTransportedItem( player )
    beam.effect = this
    beam
  }

  override def createBoltEntity( world : World, player : EntityPlayer ) : MatterTransporterBoltEntity = {
    val bolt = new MatterTransporterBoltEntity( world )
    bolt.itemId = deductTransportedItem( player )
    bolt.effect = this
    bolt
  }

  val boltTexture = new ResourceLocation( "rayguns", "textures/bolts/matter_transporter_bolt.png" )
  val beamTexture = new ResourceLocation( "rayguns", "textures/beams/matter_transporter_beam.png" )
  val chargeTexture = new ResourceLocation( "rayguns", "textures/effects/charge/matter_transporter_charge.png" )

  private def deductTransportedItem( player: EntityPlayer ) : Int = {
    val currentSlot = player.inventory.currentItem
    val itemSlot = ( currentSlot + 1 ) % 8

    val stack = player.inventory.getStackInSlot( itemSlot )
    if ( stack == null ) return 0
    val item = stack.getItem()
    if ( !item.isInstanceOf[ItemBlock] ) return 0

    stack.stackSize -= 1
    if ( stack.stackSize > 0 ) player.inventory.setInventorySlotContents( itemSlot, stack.copy )
    else player.inventory.setInventorySlotContents( itemSlot, null )

    item.itemID
  }
}

trait MatterTransporterShootable extends Shootable with IEntityAdditionalSpawnData {
  var itemId : Int = _

  abstract override def onEntityUpdate() : Unit = {
    super.onEntityUpdate()
    if ( itemId == 0 ) this.setDead()
  }

  abstract override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeEntityToNBT(tag)
    tag.setInteger("ItemId", itemId)
  }

  abstract override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readEntityFromNBT(tag)
    itemId = tag.getInteger("ItemId")
  }

  abstract override def writeSpawnData( out : ByteArrayDataOutput ) : Unit = {
    super.writeSpawnData(out)
    out.writeInt(itemId)
  }

  abstract override def readSpawnData( in : ByteArrayDataInput ) : Unit = {
    super.readSpawnData(in)
    itemId = in.readInt()
  }
}
class MatterTransporterBoltEntity(world : World) extends BaseBoltEntity(world) with MatterTransporterShootable
class MatterTransporterBeamEntity(world : World) extends BaseBeamEntity(world) with MatterTransporterShootable