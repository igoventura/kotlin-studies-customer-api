package me.igoventura.kotlin_studies_customer_api.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import me.igoventura.kotlin_studies_customer_api.dto.PageResponse
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

abstract class Service<T, ID>(
    private val repository: CoroutineCrudRepository<T, ID>
) {
    open suspend fun getAll(page: Int, size: Int): PageResponse<T> = coroutineScope {
        val dataDeferred = async {
            repository.findAll()
                .toList()
                .drop(page * size)
                .take(size)
        }

        val totalElementsDeferred = async {
            repository.count()
        }

        val data = dataDeferred.await()
        val totalElements = totalElementsDeferred.await()

        PageResponse(
            data = data,
            currentPage = page,
            pageSize = size,
            totalElements = totalElements
        )
    }
}