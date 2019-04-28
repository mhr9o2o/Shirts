package com.mhr.shirts.ui.units.shirts

import androidx.lifecycle.ViewModel;
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ShirtsViewModel : ViewModel() {

    //region Fields
    private val shirtsModel = ShirtsModel()
    private val filterSubject: BehaviorSubject<ShirtFilter> = BehaviorSubject.create()
    //endregion

    //region View-to-Model-relation Functions
    fun onSizeSet(size: String)
    {
        val colour = filterSubject.lastElement().blockingGet(ShirtFilter(DataAccessLayer.FILTER_NONE, DataAccessLayer.FILTER_NONE)).colour
        filterSubject.onNext(ShirtFilter(size, colour))
    }

    fun onColourSet(colour: String)
    {
        val size = filterSubject.lastElement().blockingGet(ShirtFilter(DataAccessLayer.FILTER_NONE, DataAccessLayer.FILTER_NONE)).size
        filterSubject.onNext(ShirtFilter(size, colour))
    }
    //endregion

    //region Model-to-View-relation Functions
    fun getShirts() : Observable<List<Shirt>>
    {
        return shirtsModel.getShirts()
    }

    fun getFilteredShirts() : Observable<List<Shirt>>
    {
        return filterSubject.observeOn(Schedulers.computation()).flatMap(shirtsModel::filterShirts)
    }
    //endregion

}
