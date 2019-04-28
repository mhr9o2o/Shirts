package com.mhr.shirts.ui.units.shirts

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import javax.inject.Inject

/**
 * ShirtsModel holds the business logic of shirts unit
 */
class ShirtsModel {

    init {
        MyApplication.instance.dataComponent.inject(this)
    }

    //region Fields
    @Inject
    lateinit var dataAccessLayer: DataAccessLayer
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

    fun filterShirts(filter: ShirtFilter = ShirtFilter(ShirtFilter.FILTER_NONE_LITERAL_TEXT,ShirtFilter.FILTER_NONE_LITERAL_TEXT)) : Observable<List<Shirt>>
    {
        dataAccessLayer.filterShirts(filter.size, filter.colour)
        return dataAccessLayer.shirts
    }
    //endregion

}