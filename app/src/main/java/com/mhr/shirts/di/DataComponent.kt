package com.mhr.shirts.di

import com.mhr.shirts.ui.activity.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class])
@Singleton
interface DataComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun context(mainActivity: MainActivity): Builder
        fun build(): DataComponent
    }

}