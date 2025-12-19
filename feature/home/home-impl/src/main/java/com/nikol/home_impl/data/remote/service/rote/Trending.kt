package com.nikol.home_impl.data.remote.service.rote

import io.ktor.resources.Resource

@Resource("trending")
class Trending {

    @Resource("{media_type}")
    class MediaType(
        val parent: Trending = Trending(),
        val media_type: String
    ) {
        @Resource("{time_window}")
        class TimeWindow(
            val parent: MediaType,
            val time_window: String
        )
    }
}