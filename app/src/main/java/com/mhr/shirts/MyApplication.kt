package com.mhr.shirts

import android.app.Application
import com.mhr.shirts.di.DaggerDataComponent
import com.mhr.shirts.di.DataComponent

class MyApplication : Application() {

    //region Fields
    lateinit var dataComponent: DataComponent
    //endregion

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataComponent = DaggerDataComponent.builder()
            .context(this)
            .build()
    }

    companion object
    {

        lateinit var instance: MyApplication
            private set
    }

}