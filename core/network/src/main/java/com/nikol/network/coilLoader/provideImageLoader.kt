package com.nikol.network.coilLoader

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import java.io.File

internal fun provideImageLoader(
    context: Context,
    httpClient: HttpClient
): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(
                KtorNetworkFetcherFactory(httpClient)
            )
        }
        .diskCache {
            DiskCache.Builder()
                .directory(File(context.cacheDir, "image_cache"))
                .maxSizeBytes(50L * 1024 * 1024)
                .build()
        }
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .weakReferencesEnabled(true)
                .build()
        }
        .build()
}