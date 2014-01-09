package com.castlebravostudios.rayguns.render

import scala.util.Random
import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.items.misc.RayGun
import cpw.mods.fml.common.ITickHandler
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.ItemRenderType
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper
import java.util.EnumSet
import cpw.mods.fml.common.TickType

object RaygunRender extends IItemRenderer with ITickHandler {

  private var partialTickTime : Float = 0.0f

  private val rand = new Random()

  def handleRenderType( item : ItemStack, renderType : ItemRenderType ) : Boolean = {
    renderType == ItemRenderType.EQUIPPED || renderType == ItemRenderType.EQUIPPED_FIRST_PERSON
  }

  def shouldUseRenderHelper( renderType : ItemRenderType, item : ItemStack, helper : ItemRendererHelper ) : Boolean = {
    false
  }

  def renderItem( renderType : ItemRenderType, item : ItemStack, data : Object* ) : Unit = {
    val renderBlocks : RenderBlocks = data(0).asInstanceOf[RenderBlocks]
    val entity : EntityLivingBase = data(1).asInstanceOf[EntityLivingBase]

    val maxCooldown = Math.max( RayGun.getBaseCooldownTime( item ), 1 )
    val curCooldown = Math.min( RayGun.getCooldownTime( item ), maxCooldown )

    val coolPercent = Math.max( 0.0f, ( curCooldown.toFloat - partialTickTime ) / maxCooldown )

    GL11.glTranslatef( coolPercent * -0.3f, coolPercent * -0.2f, 0.0f)

    if ( coolPercent > 0.0f ) {
      //println( coolPercent );
    }

    renderItem( item, entity )
  }

  private def renderItem( item : ItemStack, entity : EntityLivingBase ) : Unit = {
    val tessellator = Tessellator.instance;
    val icon = entity.getItemIcon(item, 0);
    val minU = icon.getMinU();
    val maxU = icon.getMaxU();
    val minV = icon.getMinV();
    val maxV = icon.getMaxV();
    val width = 0.0625F
    ItemRenderer.renderItemIn2D( tessellator, maxU, minV, minU, maxV,
        icon.getIconWidth(), icon.getIconHeight(), width );
  }

  def tickStart( tickType : EnumSet[TickType], tickData : Object* ) : Unit = {
    partialTickTime = tickData(0).asInstanceOf[Float]
  }

  def tickEnd( tickType : EnumSet[TickType], tickData : Object* ) : Unit = ()

  def ticks() = EnumSet.of( TickType.RENDER )

  def getLabel() = "RaygunRenderer"
}