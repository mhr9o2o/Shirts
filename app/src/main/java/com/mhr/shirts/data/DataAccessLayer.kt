package com.mhr.shirts.data

import android.content.Context
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.data.database.AppDataBase
import com.mhr.shirts.data.database.dao.BasketDao
import com.mhr.shirts.data.database.dao.ShirtDao
import com.mhr.shirts.network.NetworkAccessLayer
import com.mhr.shirts.network.models.request.OrderRequest
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import com.mhr.shirts.ui.units.shirts.ShirtFilter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.NullPointerException

class DataAccessLayer(context: Context) {

    //region Static Constants
    companion object
    {
        const val networkErrorMessage = "Network"
        const val generalErrorMessage = "General"
        const val emptyBasketErrorMessage = "Empty"
    }
    //endregion

    //region Subjects
    val shirts: PublishSubject<List<Shirt>> = PublishSubject.create()
    val basket: PublishSubject<Basket> = PublishSubject.create()
    val errors: PublishSubject<String> = PublishSubject.create()
    //endregion

    //region DataBase
    private var database: AppDataBase? = null
    private var shirtDao: ShirtDao? = null
    private var basketDao: BasketDao? = null
    //endregion

    //region Network
    private var networkAccessLayer: NetworkAccessLayer
    private val disposables: CompositeDisposable = CompositeDisposable()
    //endregion

    //region Fields
    val sizes: MutableList<String> = mutableListOf()
    val colours: MutableList<String> = mutableListOf()
    //endregion

    //region Initializer
    init {
        database = AppDataBase.getAppDataBase(context)
        shirtDao = database?.shirtDao()
        basketDao = database?.basketDao()
        networkAccessLayer = NetworkAccessLayer()
    }
    //endregion

    //region Access Functions

    //region Shirts
    fun fetchShirts()
    {

        fetchAndPublishShirtsFromDataBase(true)

        disposables.add(fetchShirtsFromServer()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                updateShirts(it, false)
                shirts.onNext(it)
            },
            {
                errors.onNext(networkErrorMessage)
            }
        ))
    }

    fun filterShirts(size: String, colour: String)
    {
        if (size == ShirtFilter.FILTER_NONE_LITERAL_TEXT && colour == ShirtFilter.FILTER_NONE_LITERAL_TEXT)
        {
            fetchAndPublishShirtsFromDataBase(false)
        }
        else if (size == ShirtFilter.FILTER_NONE_LITERAL_TEXT)
        {
            disposables.add(Observable.fromCallable {
                database!!.shirtDao().filterShirtsByColour(colour)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        shirts.onNext(it)
                    },
                    {
                        errors.onNext(generalErrorMessage)
                    }
                ))
        }
        else if (colour == ShirtFilter.FILTER_NONE_LITERAL_TEXT)
        {
            disposables.add(Observable.fromCallable {
                database!!.shirtDao().filterShirtsBySize(size)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        shirts.onNext(it)
                    },
                    {
                        errors.onNext(generalErrorMessage)
                    }
                ))
        }
        else
        {
            disposables.add(Observable.fromCallable {
                database!!.shirtDao().filterShirtsByColourAndSize(colour, size)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        shirts.onNext(it)
                    },
                    {
                        errors.onNext(generalErrorMessage)
                    }
                ))
        }
    }

    //endregion

    //region Basket
    fun fetchBasket()
    {
        disposables.add(Observable.fromCallable {
            database!!.basketDao().getBasket()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    basket.onNext(it)
                },
                {
                    if (it is NullPointerException)
                    {
                        val basket = Basket()
                        updateDataBasket(basket)
                    }
                    else
                    {
                        it.message?.let { message -> errors.onNext(message) }
                    }

                }
            ))
    }

    fun clearBasket(basket: Basket)
    {
        disposables.add(Observable.fromCallable {
            database!!.basketDao().deleteBasket(basket)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    this.basket.onNext(Basket())
                },
                {
                    errors.onNext(generalErrorMessage)
                }
            ))
    }

    fun updateDataBasket(basket: Basket)
    {
        disposables.add(Observable.fromCallable {
            database!!.basketDao().insertBasket(basket)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    this.basket.onNext(basket)
                },
                {
                    errors.onNext(generalErrorMessage)
                }
            ))
    }

    fun orderBasket(basket: Basket, totalCost: Int) : Observable<SuccessfulOrderResponse>
    {
        return networkAccessLayer.orderBasket(OrderRequest(totalCost, basket))
    }
    //endregion

    //endregion

    //region Inner Data Functions
    private fun fetchShirtsFromServer() : Observable<List<Shirt>>?
    {
        return networkAccessLayer.fetchAllShirts()
    }

    private fun retrieveShirtsFromDataBase() : Observable<List<Shirt>>
    {
        return Observable.fromCallable {
            database!!.shirtDao().getShirts()
        }
    }

    private fun fetchAndPublishShirtsFromDataBase(shouldUpdate: Boolean)
    {
        disposables.add(retrieveShirtsFromDataBase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                if (shouldUpdate) updateShirts(it, true)
                shirts.onNext(it)
            },
            {
                errors.onNext(generalErrorMessage)
            }
        ))
    }

    private fun updateShirts(shirts: List<Shirt>, isCachedData: Boolean)
    {
        for (shirt in shirts)
        {
            if (!sizes.contains(shirt.size) && shirt.size != null) sizes.add(shirt.size)
            if (!colours.contains(shirt.colour) && shirt.colour != null) colours.add(shirt.colour)
            if (!isCachedData) updateDataBase(shirt)
        }
    }

    private fun updateDataBase(shirt: Shirt)
    {
        disposables.add(Observable.fromCallable {
            shirtDao!!.insertShirt(shirt)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())

    }
    //endregion

    //region Lifecycle-wise Functions
    fun dispose()
    {
        disposables.clear()
    }

    fun closeDataBase()
    {
        database?.close()
        AppDataBase.killDataBase()
    }
    //endregion

}