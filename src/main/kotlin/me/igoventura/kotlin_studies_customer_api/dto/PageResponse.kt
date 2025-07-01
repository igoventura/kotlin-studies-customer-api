package me.igoventura.kotlin_studies_customer_api.dto

data class PageResponse<T>(
    val data: List<T>,
    val currentPage: Int,
    val pageSize: Int,
    val totalElements: Long
)
