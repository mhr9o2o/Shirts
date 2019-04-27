package com.mhr.shirts.ui.units.basket

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable

class BasketViewModel : ViewModel() {

    //region Fields
    private val basketModel = BasketModel()
    //region

    //region Model-relation Functions
    fun unitIsReady() : Disposable
    {
        return basketModel.fetchData()
    }
    //endregion

    //region View-relation Functions
    //endregion

}
