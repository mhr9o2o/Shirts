package com.mhr.shirts.ui.units.basket

import androidx.lifecycle.ViewModel;
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable
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
    fun orderBasket() : Observable<SuccessfulOrderResponse>
    {
        return basketModel.order()
    }

    fun getBasketItems() : Observable<List<Shirt>>
    {
        return basketModel.basketItemsSubject
    }

    fun getTotalCost() : Observable<Int>
    {
        return basketModel.totalCostSubject
    }
    //endregion

}
