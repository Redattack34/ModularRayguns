package com.castlebravostudios.rayguns.api.defaults

import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.api.items.ItemBattery

abstract class DefaultItemBattery(id: Int) extends DefaultItemModule(id) with ItemBattery {

}