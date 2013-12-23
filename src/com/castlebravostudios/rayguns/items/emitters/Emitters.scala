package com.castlebravostudios.rayguns.items.emitters

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.RecipeRegisterer._
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.block.Block
import net.minecraft.item.ItemStack

object Emitters {

  class Emitter( id : Int ) extends Item( id ) {
    setCreativeTab( ModularRayguns.raygunsTab )

    def registerRecipe( tier : RecipeTier, item : AnyRef) : Emitter =
      registerRecipe( tier, item, item, item, item)
    def registerRecipe( tier : RecipeTier, vert : AnyRef, horiz: AnyRef) : Emitter =
      registerRecipe( tier, vert, horiz, vert, horiz )
    def registerRecipe( tier : RecipeTier, top : AnyRef, right : AnyRef, bottom : AnyRef, left : AnyRef ) : Emitter = {
      registerEmitter(tier, this, top, right, bottom, left)
      this
    }
  }

  val laserEmitter = new Emitter( Config.emitterLaser )
    .registerRecipe( Tier1, Item.redstone )
    .setUnlocalizedName("rayguns.LaserEmitter")
    .setTextureName("rayguns:emitter_laser")

  val heatRayEmitter = new Emitter( Config.emitterHeatRay )
    .registerRecipe( Tier1, Item.coal, Item.bucketLava )
    .setUnlocalizedName("rayguns.HeatRayEmitter")
    .setTextureName("rayguns:emitter_heat_ray")

  val frostRayEmitter = new Emitter( Config.emitterFrostRay )
    .registerRecipe( Tier2, Block.ice, Block.snow )
    .setUnlocalizedName("rayguns.FrostRayEmitter")
    .setTextureName("rayguns:emitter_frost_ray")

  val lifeForceEmitter = new Emitter( Config.emitterLifeForce )
    .registerRecipe( Tier2, Item.speckledMelon, Item.ghastTear )
    .setUnlocalizedName("rayguns.LifeForceEmitter")
    .setTextureName("rayguns:emitter_life_force")

  val fortifiedSunlightEmitter = new Emitter( Config.emitterFortifiedSunlight )
    .registerRecipe( Tier2, Block.wood )
    .setUnlocalizedName("rayguns.FortifiedSunlightEmitter")
    .setTextureName("rayguns:emitter_fortified_sunlight")

  val explosiveEmitter = new Emitter( Config.emitterExplosive )
    .registerRecipe( Tier3, Block.tnt )
    .setUnlocalizedName("rayguns.ExplosiveEmitter")
    .setTextureName("rayguns:emitter_explosive")

  val deathRayEmitter = new Emitter( Config.emitterDeathRay )
    .registerRecipe( Tier3, new ItemStack( Item.skull, 1, 1 ) )
    .setUnlocalizedName("rayguns.DeathRayEmitter")
    .setTextureName("rayguns:emitter_death_ray")

  val enderEmitter = new Emitter( Config.emitterEnder )
    .registerRecipe( Tier2, Item.enderPearl )
    .setUnlocalizedName("rayguns.EnderEmitter")
    .setTextureName("rayguns:emitter_ender")

  val lightningEmitter = new Emitter( Config.emitterLightning )
    .registerRecipe( Tier2, Block.blockIron, Block.blockRedstone )
    .setUnlocalizedName("rayguns.LightningEmitter")
    .setTextureName("rayguns:emitter_lightning")

  val impulseEmitter = new Emitter( Config.emitterImpulse )
    .registerRecipe( Tier2, Block.pistonBase )
    .setUnlocalizedName("rayguns.ImpulseEmitter")
    .setTextureName("rayguns:emitter_impulse")

  val tractorEmitter = new Emitter( Config.emitterTractor )
    .registerRecipe( Tier2, Block.pistonStickyBase )
    .setUnlocalizedName("rayguns.TractorEmitter")
    .setTextureName("rayguns:emitter_tractor")

  val tier1CuttingEmitter = new Emitter( Config.emitterTier1Cutting )
    .registerRecipe( Tier1, Item.pickaxeStone, Item.shovelStone )
    .setUnlocalizedName("rayguns.Tier1CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t1")

  val tier2CuttingEmitter = new Emitter( Config.emitterTier2Cutting )
    .registerRecipe( Tier1, Item.pickaxeIron, Item.shovelIron )
    .setUnlocalizedName("rayguns.Tier2CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t2")

  val tier3CuttingEmitter = new Emitter( Config.emitterTier3Cutting )
    .registerRecipe( Tier3, Item.pickaxeDiamond, Item.shovelDiamond )
    .setUnlocalizedName("rayguns.Tier3CuttingEmitter")
    .setTextureName("rayguns:emitter_cutting_t3")

  val shrinkRayEmitter = new Emitter( Config.emitterShrinkRay )
    .registerRecipe( Tier1, Block.pistonBase )
    .setUnlocalizedName("rayguns.ShrinkRayEmitter")
    .setTextureName("rayguns:emitter_shrink_ray")
}