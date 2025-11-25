package com.nikol.reelup.application

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.nikol.reelup.di.mainModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ReelUpApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@ReelUpApplication)
            modules(mainModule)
        }
    }

    override fun deleteDatabase(name: String?): Boolean {
        return super.deleteDatabase(name)
    }

    private val imageLoader: ImageLoader by inject()

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader

}