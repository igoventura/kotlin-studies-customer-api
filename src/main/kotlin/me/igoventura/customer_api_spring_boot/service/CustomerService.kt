package me.igoventura.customer_api_spring_boot.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import me.igoventura.customer_api_spring_boot.dto.CustomerRequest
import me.igoventura.customer_api_spring_boot.dto.CustomerResponse
import me.igoventura.customer_api_spring_boot.dto.PageResponse
import me.igoventura.customer_api_spring_boot.dto.toModel
import me.igoventura.customer_api_spring_boot.dto.toResponse
import me.igoventura.customer_api_spring_boot.exception.NotFoundException
import me.igoventura.customer_api_spring_boot.model.Customer
import me.igoventura.customer_api_spring_boot.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    suspend fun getAll(page: Int, size: Int): PageResponse<CustomerResponse> = coroutineScope {
        val customersDeferred = async {
            customerRepository.findAll()
                .toList()
                .drop(page * size)
                .take(size)
                .map { it.toResponse() }
        }

        val totalElementsDeferred = async {
            customerRepository.count()
        }

        val customers = customersDeferred.await()
        val totalElements = totalElementsDeferred.await()

        PageResponse(
            data = customers,
            currentPage = page,
            pageSize = size,
            totalElements = totalElements
        )
    }

    suspend fun getById(id: Long): CustomerResponse {
        return getByIdOrThrow(id).toResponse()
    }

    suspend fun create(request: CustomerRequest): CustomerResponse {
        return customerRepository
            .save(request.toModel())
            .toResponse()
    }

    suspend fun update(id: Long, request: CustomerRequest): CustomerResponse {
        return customerRepository
            .save(
                getByIdOrThrow(id)
                    .copy(
                        name = request.name,
                        email = request.email
                    )
            ).toResponse()
    }

    suspend fun delete(id: Long) {
        customerRepository.delete(getByIdOrThrow(id))
    }

    private suspend fun getByIdOrThrow(id: Long): Customer {
        return customerRepository.findById(id)
            ?: throw NotFoundException("Customer not found for id $id")
    }
}