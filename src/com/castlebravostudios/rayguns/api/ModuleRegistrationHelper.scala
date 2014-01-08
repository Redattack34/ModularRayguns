package com.castlebravostudios.rayguns.api

import com.castlebravostudios.rayguns.api.items.RaygunModule
import com.castlebravostudios.rayguns.api.items.RaygunChamber

/**
 * Helper class which will automatically register a module in all of the various
 * places it needs to be registered.
 */
object ModuleRegistrationHelper {

  /**
   * Register the given module with the ModuleRegistry, register the ItemModule,
   * If the module is a chamber, registers the beam handlers.
   * If ID is less than or equal to zero, does nothing (to allow modules to be disabled).
   */
  def registerModule( module : RaygunModule, id : Int ) : Unit = {
    if ( id <= 0 ) { return; }

    module.registerItem(id)
    ModuleRegistry.registerModule(module)

    module match {
      case chamber : RaygunChamber => chamber.registerShotHandlers()
      case _ => ()
    }
  }
}