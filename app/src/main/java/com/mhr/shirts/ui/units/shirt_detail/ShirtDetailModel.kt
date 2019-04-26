package com.mhr.shirts.ui.units.shirt_detail

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import javax.inject.Inject

class ShirtDetailModel {

    init {
        MyApplication.instance.dataComponent.inject(this)
    }

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    //endregion

    //region Functions
    fun getBasket() : Observable<Basket>
    {
        dataAccessLayer.fetchBasket()
        return dataAccessLayer.basket
    }

    fun updateBasket(basket: Basket)
    {
        dataAccessLayer.updateDataBasket(basket)
    }
    //endregion

}