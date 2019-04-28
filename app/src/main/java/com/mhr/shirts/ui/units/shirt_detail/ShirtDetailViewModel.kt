package com.mhr.shirts.ui.units.shirt_detail

import androidx.lifecycle.ViewModel
import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.disposables.Disposable

/**
 * ShirtDetailViewModel acts as a connector between view [ShirtDetailFragment] and logic [ShirtDetailModel]
 */
class ShirtDetailViewModel : ViewModel() {

    //region Fields
    private val shirtDetailModel = ShirtDetailModel()
    //endregion

    //region View-to-Model-relation Functions
    fun unitIsReady(): Disposable {
        return shirtDetailModel.fetchData()
    }

    fun onAddToBasketClicked(shirt: Shirt) {
        shirtDetailModel.addShirtToBasket(shirt)
    }
    //endregion

    //region Model-to-View-relation Functions

    //endregion

}
