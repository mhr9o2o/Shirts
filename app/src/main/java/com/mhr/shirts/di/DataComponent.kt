package com.mhr.shirts.di

import com.mhr.shirts.MyApplication
import com.mhr.shirts.ui.activity.MainActivity
import com.mhr.shirts.ui.units.basket.BasketModel
import com.mhr.shirts.ui.units.shirt_detail.ShirtDetailModel
import com.mhr.shirts.ui.units.shirts.ShirtsModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Our DataComponent which proveds DataAccessLayer
 */
@Component(modules = [DataModule::class])
@Singleton
interface DataComponent {

    // We want to limit the access to the DataAccessLayer
    fun inject(mainActivity: MainActivity)

    fun inject(basketModel: BasketModel)
    fun inject(shirtsModel: ShirtsModel)
    fun inject(shirtDetailModel: ShirtDetailModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: MyApplication): Builder

        fun build(): DataComponent
    }

}