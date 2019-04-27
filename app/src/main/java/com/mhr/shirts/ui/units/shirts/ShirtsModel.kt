package com.mhr.shirts.ui.units.shirts

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import javax.inject.Inject

class ShirtsModel {

    init {
        MyApplication.instance.dataComponent.inject(this)
    }

    //region Fields
    @Inject
    private lateinit var dataAccessLayer: DataAccessLayer
    //endregion

    //region Functions
    fun getShirts() : Observable<List<Shirt>>
    {
        dataAccessLayer.fetchShirts()
        return dataAccessLayer.shirts
    }

    fun getSizes() : List<String>
    {
        return dataAccessLayer.sizes
    }

    fun getColours() : List<String>
    {
        return dataAccessLayer.colours
    }

    fun filterShirts(filter: ShirtFilter = ShirtFilter(DataAccessLayer.FILTER_NONE, DataAccessLayer.FILTER_NONE)) : Observable<List<Shirt>>
    {
        dataAccessLayer.filterShirts(filter.size, filter.colour)
        return dataAccessLayer.shirts
    }
    //endregion

}