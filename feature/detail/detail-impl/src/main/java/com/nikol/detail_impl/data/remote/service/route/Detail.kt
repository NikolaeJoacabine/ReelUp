package com.nikol.detail_impl.data.remote.service.route

import io.ktor.resources.Resource

@Resource("movie")
class Movie {

    @Resource("{id}")
    class Id(
        val parent: Movie = Movie(),
        val id: Int,
        val append_to_response: String = "credits,videos,recommendations,similar,account_states,watch/providers,release_dates",
        val include_image_language: String = "en,null"
    )
}

@Resource("tv")
class Tv {

    @Resource("{id}")
    class Id(
        val parent: Tv = Tv(),
        val id: Int,
        val append_to_response: String = "credits,videos,recommendations,similar,account_states,watch/providers,content_ratings",
        val include_image_language: String = "en,null"
    )
}

@Resource("person")
class Person {

    @Resource("{id}")
    class Id(
        val parent: Person = Person(),
        val id: Int,
        val append_to_response: String = "combined_credits,images"
    )
}