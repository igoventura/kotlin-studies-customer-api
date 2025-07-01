package me.igoventura.kotlin_studies_customer_api.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.igoventura.kotlin_studies_customer_api.dto.OrderRequest
import me.igoventura.kotlin_studies_customer_api.dto.OrderResponse
import me.igoventura.kotlin_studies_customer_api.dto.toModel
import me.igoventura.kotlin_studies_customer_api.dto.toResponse
import me.igoventura.kotlin_studies_customer_api.exception.NotFoundException
import me.igoventura.kotlin_studies_customer_api.model.Customer
import me.igoventura.kotlin_studies_customer_api.repository.CustomerRepository
import me.igoventura.kotlin_studies_customer_api.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository
) {
    suspend fun create(customerId: Long, request: OrderRequest): OrderResponse {
        getCustomerByIdOrThrow(customerId)

        return orderRepository
            .save(request.toModel(customerId))
            .toResponse()
    }

    suspend fun getAllByCustomerId(customerId: Long): Flow<OrderResponse> {
        getCustomerByIdOrThrow(customerId)

        return orderRepository.findByCustomerId(customerId).map { it.toResponse() }
    }

    private suspend fun getCustomerByIdOrThrow(id: Long): Customer {
        return customerRepository.findById(id)
            ?: throw NotFoundException("Customer not found for id $id")
    }
}