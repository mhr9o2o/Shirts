package com.mhr.shirts

import android.app.Application
import com.mhr.shirts.di.DaggerDataComponent
import com.mhr.shirts.di.DataComponent

/**
 * A customization for android application class to give us access to a static instance and built DataComponent
 */
class MyApplication : Application() {

    //region Fields
    lateinit var dataComponent: DataComponent
    //endregion

    //region Overridden Functions
    override fun onCreate() {
        super.onCreate()
        instance = this
        dataComponent = DaggerDataComponent.builder()
            .context(this)
            .build()
    }
    //endregion

    //region Static Instance
    companion object {

        lateinit var instance: MyApplication
            private set
    }
    //endregion

}