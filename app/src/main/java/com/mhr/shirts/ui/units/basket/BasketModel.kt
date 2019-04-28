package com.mhr.shirts.ui.units.basket

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * BasketModel holds the business logic of basket unit
 */
class BasketModel {

    init {
        MyApplication.instance.dataComponent.inject(this)
    }

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
    val totalCostSubject: PublishSubject<Int> = PublishSubject.create()
    val basketItemsSubject: PublishSubject<List<Shirt>> = PublishSubject.create()
    private lateinit var basket: Basket
    private var totalCost = 0
    //endregion

    //region Functions
    fun fetchData(): Disposable {
        return getBasket().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                basket = it
                totalCostSubject.onNext(updateTotalCost(basket))
                basketItemsSubject.onNext(basket.shirts)
            }
    }

    fun addShirtToBasket(shirt: Shirt) {
        checkExistenceThenAdd(shirt, basket)
        updateBasket(basket)
    }

    fun removeShirtFromBasket(shirt: Shirt) {
        removeIfPossibleOrDelete(shirt, basket)
        updateBasket(basket)
    }

    fun deleteShirtFromBasket(shirt: Shirt) {
        checkExistenceAndDelete(shirt, basket)
        updateBasket(basket)
    }

    fun order(): Observable<SuccessfulOrderResponse> {
        return if (basket.shirts.isEmpty()) {
            Observable.error(Throwable(DataAccessLayer.emptyBasketErrorMessage))
        } else {
            dataAccessLayer.orderBasket(basket = basket, totalCost = totalCost).doOnNext {
                clearBasket(basket)
            }
        }

    }

    private fun getBasket(): Observable<Basket> {
        dataAccessLayer.fetchBasket()
        return dataAccessLayer.basket
    }

    internal fun checkExistenceThenAdd(shirt: Shirt, basket: Basket) {
        if (basket.shirts.contains(shirt)) {
            val index = basket.shirts.indexOf(shirt)
            if (index != -1) {
                val currentQuantity = basket.shirts[index].quantity ?: 0
                basket.shirts[index].quantity = currentQuantity + 1
            }
        } else {
            shirt.quantity = 1
            basket.shirts.add(shirt)
        }
    }

    internal fun removeIfPossibleOrDelete(shirt: Shirt, basket: Basket) {
        if (basket.shirts.contains(shirt)) {
            val index = basket.shirts.indexOf(shirt)
            if (index != -1) {
                val quantity = basket.shirts[index].quantity ?: 0
                if (quantity > 1) {
                    basket.shirts[index].quantity = quantity - 1
                } else {
                    delete(shirt, basket)
                }
            }
        }
    }

    internal fun checkExistenceAndDelete(shirt: Shirt, basket: Basket) {
        if (basket.shirts.contains(shirt)) {
            delete(shirt, basket)
        }
    }

    internal fun delete(shirt: Shirt, basket: Basket) {
        basket.shirts.remove(shirt)
    }

    internal fun updateTotalCost(basket: Basket): Int {
        totalCost = 0
        for (shirt in basket.shirts) {
            totalCost += (shirt.quantity ?: 0) * (shirt.price ?: 0)
        }
        return totalCost
    }

    private fun updateBasket(basket: Basket) {
        dataAccessLayer.updateDataBasket(basket)
    }

    private fun clearBasket(basket: Basket) {
        dataAccessLayer.clearBasket(basket)
    }
    //endregion

}