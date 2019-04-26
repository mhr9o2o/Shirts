package com.mhr.shirts.data

import android.content.Context
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.data.database.AppDataBase
import com.mhr.shirts.data.database.dao.BasketDao
import com.mhr.shirts.data.database.dao.ShirtDao
import com.mhr.shirts.network.NetworkAccessLayer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class DataAccessLayer(context: Context) {

    //region Subjects
    val shirts: PublishSubject<List<Shirt>> = PublishSubject.create()
    val basket: PublishSubject<Basket> = PublishSubject.create()
    //endregion

    //region DataBase
    private var database: AppDataBase? = null
    private var shirtDao: ShirtDao? = null
    private var basketDao: BasketDao? = null
    //endregion

    //region Network
    private var networkAccessLayer: NetworkAccessLayer? = null
    private val disposables: CompositeDisposable = CompositeDisposable()
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
    //endregion

    //region Lifecycle-wise Functions
    fun dispose()
    {
        disposables.dispose()
    }
    //endregion

}