package com.nikol.detail_impl.domain.model

sealed interface ContentDetailDomain {
    val id: Int
    val title: String          // Унифицируем title/name
    val originalTitle: String  // original_title/original_name
    val description: String    // overview

    // Визуал (полные ссылки)
    val posterUrl: String?
    val backdropUrl: String?

    // Мета
    val rating: Double
    val genres: List<String>
    val releaseDate: String?   // release_date / first_air_date
    val status: String         // Released / Ended / Returning

    // Списки
    val cast: List<PersonDomain>
    val trailers: List<VideoDomain>
    val recommendations: List<ContentSummaryDomain>

    // Статус пользователя (из account_states)
    val isFavorite: Boolean
    val isInWatchlist: Boolean

    val productionCompanies: List<ProductionCompanyDomain>
}