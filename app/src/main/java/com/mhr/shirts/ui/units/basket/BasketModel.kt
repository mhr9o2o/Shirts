package com.mhr.shirts.ui.units.basket

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

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
    //endregion

    //region Functions
    fun fetchData() : Disposable
    {
        return getBasket().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                basket = it
                updateTotalCost(basket)
                basketItemsSubject.onNext(basket.shirts)
            }
    }

    fun addShirtToBasket(shirt: Shirt)
    {
        checkExistenceThenAdd(shirt, basket)
        updateBasket(basket)
    }

    fun removeShirtFromBasket(shirt: Shirt)
    {
        removeIfPossibleOrDelete(shirt, basket)
        updateBasket(basket)
    }

    fun deleteShirtFromBasket(shirt: Shirt)
    {
        checkExistenceAndDelete(shirt, basket)
        updateBasket(basket)
    }

    protected fun getBasket() : Observable<Basket>
    {
        dataAccessLayer.fetchBasket()
        return dataAccessLayer.basket
    }

    protected fun checkExistenceThenAdd(shirt: Shirt, basket: Basket)
    {
        if (basket.shirts.contains(shirt))
        {
            val index = basket.shirts.indexOf(shirt)
            if (index != -1)
            {
                val currentQuantity = basket.shirts[index].quantity?:0
                basket.shirts[index].quantity = currentQuantity + 1
            }
        }
        else
        {
            shirt.quantity = 1
            basket.shirts.add(shirt)
        }
    }

    protected fun removeIfPossibleOrDelete(shirt: Shirt, basket: Basket)
    {
        if (basket.shirts.contains(shirt))
        {
            val index = basket.shirts.indexOf(shirt)
            if (index != -1)
            {
                val quantity = basket.shirts[index].quantity?:0
                if (quantity > 1)
                {
                    basket.shirts[index].quantity = quantity - 1
                }
                else {
                    delete(shirt, basket)
                }
            }
        }
    }

    protected fun checkExistenceAndDelete(shirt: Shirt, basket: Basket)
    {
        if (basket.shirts.contains(shirt))
        {
            delete(shirt, basket)
        }
    }

    protected fun delete(shirt: Shirt, basket: Basket)
    {
        basket.shirts.remove(shirt)
    }

    protected fun updateTotalCost(basket: Basket)
    {
        var cost = 0
        for (shirt in basket.shirts)
        {
            cost += (shirt.quantity?:0) * (shirt.price?:0)
        }
        totalCostSubject.onNext(cost)
    }

    protected fun updateBasket(basket: Basket)
    {
        dataAccessLayer.updateDataBasket(basket)
    }

    protected fun clearBasket(basket: Basket)
    {
        dataAccessLayer.clearBasket(basket)
    }
    //endregion

}