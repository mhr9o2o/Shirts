package com.mhr.shirts.di

import android.content.Context
import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.ui.activity.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideContext(myApplication: MyApplication) : Context
    {
        return myApplication
    }

    @Provides
    @Singleton
    fun provideDataAccessLayer(context: Context) : DataAccessLayer
    {
        return DataAccessLayer(context)
    }

}