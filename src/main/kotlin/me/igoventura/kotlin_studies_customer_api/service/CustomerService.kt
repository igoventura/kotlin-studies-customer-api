package me.igoventura.kotlin_studies_customer_api.service

import me.igoventura.kotlin_studies_customer_api.dto.CustomerRequest
import me.igoventura.kotlin_studies_customer_api.dto.CustomerResponse
import me.igoventura.kotlin_studies_customer_api.dto.PageResponse
import me.igoventura.kotlin_studies_customer_api.dto.toModel
import me.igoventura.kotlin_studies_customer_api.dto.toResponse
import me.igoventura.kotlin_studies_customer_api.exception.NotFoundException
import me.igoventura.kotlin_studies_customer_api.model.Customer
import me.igoventura.kotlin_studies_customer_api.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): me.igoventura.kotlin_studies_customer_api.service.Service<Customer, Long>(customerRepository){
    suspend fun getAllByPage(page: Int, size: Int): PageResponse<CustomerResponse> {
        val dbPage = super.getAll(page, size)
        return PageResponse(
            data = dbPage.data.map { it.toResponse() },
            currentPage = dbPage.currentPage,
            pageSize = dbPage.pageSize,
            totalElements = dbPage.totalElements
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