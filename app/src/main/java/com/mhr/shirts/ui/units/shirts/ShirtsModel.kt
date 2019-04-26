package com.mhr.shirts.ui.units.shirts

import com.mhr.shirts.data.DataAccessLayer
import javax.inject.Inject

class ShirtsModel {

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    //endregion

}