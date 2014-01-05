package com.castlebravostudios.rayguns.api.items


abstract class BaseRaygunModule extends RaygunModule {

  private[this] var _item : ItemModule = _

  def item : ItemModule = this._item

  def registerItem( id : Int ) : Unit = {
    if ( id <= 0 ) {
      this._item = createItem( id )
    }
  }

  /**
   * Create an ItemModule with the given ID and configure it as necessary. ID
   * will always be greater than zero.
   */
  protected def createItem( id : Int ) : ItemModule
}