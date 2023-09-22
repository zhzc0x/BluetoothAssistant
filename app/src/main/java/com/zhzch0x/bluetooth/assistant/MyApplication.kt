package com.zhzch0x.bluetooth.assistant

import android.app.Application
import com.zhzc0x.bluetooth.BuildConfig
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}