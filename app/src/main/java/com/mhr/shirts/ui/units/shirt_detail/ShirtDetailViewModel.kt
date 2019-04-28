package com.mhr.shirts.ui.units.shirt_detail

import androidx.lifecycle.ViewModel;
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.ui.units.shirts.ShirtsModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class ShirtDetailViewModel : ViewModel() {

    //region Fields
    private val shirtDetailModel = ShirtDetailModel()
    //endregion

    //region View-to-Model-relation Functions
    fun unitIsReady() : Disposable
    {
        return shirtDetailModel.fetchData()
    }

    fun onAddToBasketClicked(shirt: Shirt)
    {
        shirtDetailModel.addShirtToBasket(shirt)
    }
    //endregion

    //region Model-to-View-relation Functions

    //endregion

}
