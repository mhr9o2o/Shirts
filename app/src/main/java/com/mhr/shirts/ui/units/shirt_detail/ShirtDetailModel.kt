package com.mhr.shirts.ui.units.shirt_detail

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShirtDetailModel {

    init {
        MyApplication.instance.dataComponent.inject(this)
    }

    //region Fields
    @Inject
    private lateinit var dataAccessLayer: DataAccessLayer
    private lateinit var basket: Basket
    //endregion

    //region Functions
    fun fetchData() : Disposable
    {
        return getBasket().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                basket = it
            }
    }

    fun addShirtToBasket(shirt: Shirt)
    {
        checkShirtExistenceAndUpdateTheBasket(shirt, basket)
        updateBasket(basket)
    }

    private fun getBasket() : Observable<Basket>
    {
        dataAccessLayer.fetchBasket()
        return dataAccessLayer.basket
    }

    private fun updateBasket(basket: Basket)
    {
        dataAccessLayer.updateDataBasket(basket)
    }

    private fun checkShirtExistenceAndUpdateTheBasket(shirt: Shirt, basket: Basket)
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
    //endregion

}