package com.nikol.home_impl.data.remote.service.rote

import io.ktor.resources.Resource

@Resource("movie")
class Movie {

    @Resource("now_playing")
    class NowPlaying(val parent: Movie = Movie())
}