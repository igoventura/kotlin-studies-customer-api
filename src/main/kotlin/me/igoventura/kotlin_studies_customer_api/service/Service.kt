package me.igoventura.kotlin_studies_customer_api.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import me.igoventura.kotlin_studies_customer_api.dto.PageResponse
import me.igoventura.kotlin_studies_customer_api.repository.PageableRepository
import org.springframework.data.domain.PageRequest

abstract class Service<T, ID>(
    private val repository: PageableRepository<T, ID>
) {
    open suspend fun getAll(page: Int, size: Int): PageResponse<T> = coroutineScope {
        val dataDeferred = async {
            repository.findAllBy(PageRequest.of(page, size))
                .toList()
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