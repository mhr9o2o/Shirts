package com.mhr.shirts.ui.units.shirts

import androidx.lifecycle.ViewModel
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * ShirtsViewModel acts as a connector between view [ShirtsFragment] and logic [ShirtsModel]
 */
class ShirtsViewModel : ViewModel() {

    //region Fields
    private val shirtsModel = ShirtsModel()
    private val filterSubject: BehaviorSubject<ShirtFilter> = BehaviorSubject.create()
    private var lastColour = ShirtFilter.FILTER_NONE_LITERAL_TEXT
    private var lastSize = ShirtFilter.FILTER_NONE_LITERAL_TEXT
    //endregion

    //region View-to-Model-relation Functions
    fun onSizeSet(size: String) {
        lastSize = size
        filterSubject.onNext(ShirtFilter(size, lastColour))
    }

    fun onColourSet(colour: String) {
        lastColour = colour
        filterSubject.onNext(ShirtFilter(lastSize, colour))
    }
    //endregion

    //region Model-to-View-relation Functions
    fun getAvailableSizeFilters(): List<String> {
        return shirtsModel.getSizes()
    }

    fun getAvailableColourFilters(): List<String> {
        return shirtsModel.getColours()
    }

    fun getShirts(): Observable<List<Shirt>> {
        return shirtsModel.getShirts()
    }

    fun getFilteredShirts(): Observable<List<Shirt>> {
        return filterSubject.observeOn(Schedulers.computation()).flatMap(shirtsModel::filterShirts)
    }
    //endregion

}
