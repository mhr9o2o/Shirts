package com.mhr.shirts.ui.units.basket

import androidx.lifecycle.ViewModel;
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * BasketViewModel acts as a connector between view [BasketFragment] and logic [BasketModel]
 */
class BasketViewModel : ViewModel() {

    //region Fields
    private val basketModel = BasketModel()
    //region

    //region View-to-Model-relation Functions
    fun unitIsReady(): Disposable {
        return basketModel.fetchData()
    }

    fun onShirtAdded(shirt: Shirt) {
        basketModel.addShirtToBasket(shirt)
    }

    fun onShirtRemoved(shirt: Shirt) {
        basketModel.removeShirtFromBasket(shirt)
    }

    fun onShirtDeleted(shirt: Shirt) {
        basketModel.deleteShirtFromBasket(shirt)
    }

    fun onOrderClicked(): Observable<SuccessfulOrderResponse> {
        return basketModel.order()
    }
    //endregion

    //region Model-to-View-relation Functions
    fun getBasketItems(): Observable<List<Shirt>> {
        return basketModel.basketItemsSubject
    }

    fun getTotalCost(): Observable<Int> {
        return basketModel.totalCostSubject
    }
    //endregion

}
