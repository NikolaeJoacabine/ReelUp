package com.nikol.home_impl.domain.parameters

data class ContentParameter(
    val period: Period = Period.Day
)

enum class Period {
    Week,
    Day
}

enum class TypeContent {
    Movie,
    TV
}
