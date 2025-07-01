package me.igoventura.customer_api_spring_boot.dto

data class PageResponse<T>(
    val data: List<T>,
    val currentPage: Int,
    val pageSize: Int,
    val totalElements: Long
)
