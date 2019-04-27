package com.mhr.shirts.ui.units.basket

import com.mhr.shirts.data.data_models.Shirt

class BasketAdapter {

    //region Fields
    //endregion

    //region Overridden Functions
    //endregion

    //region ViewHolder
    //endregion

    //region Callbacks
    interface BasketItemInteractionsListener
    {
        fun onAddButtonClickedFor(shirt: Shirt)
        fun onRemoveButtonClickedFor(shirt: Shirt)
        fun onDeleteButtonClickedFor(shirt: Shirt)
    }
    //endregion

}