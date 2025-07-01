package me.igoventura.customer_api_spring_boot.service

import me.igoventura.customer_api_spring_boot.dto.CustomerRequest
import me.igoventura.customer_api_spring_boot.dto.CustomerResponse
import me.igoventura.customer_api_spring_boot.dto.toModel
import me.igoventura.customer_api_spring_boot.dto.toResponse
import me.igoventura.customer_api_spring_boot.exception.NotFoundException
import me.igoventura.customer_api_spring_boot.model.Customer
import me.igoventura.customer_api_spring_boot.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    fun getAll(pageable: Pageable): Page<CustomerResponse> {
        val dbCustomers = customerRepository.findAll(pageable)

        return dbCustomers.map { it.toResponse() }
    }

    fun getById(id: Long): CustomerResponse {
        val dbCustomer = getByIdOrThrow(id)

        return dbCustomer.toResponse()
    }

    fun create(request: CustomerRequest): CustomerResponse {
        val dbCustomer = customerRepository.save(request.toModel())
        return dbCustomer.toResponse()
    }

    fun update(id: Long, request: CustomerRequest): CustomerResponse {
        val dbCustomer = getByIdOrThrow(id)

        dbCustomer.name = request.name
        dbCustomer.email = request.email

        return customerRepository.save(dbCustomer).toResponse()
    }

    fun delete(id: Long) {
        val dbCustomer = getByIdOrThrow(id)
        customerRepository.delete(dbCustomer)
    }

    private fun getByIdOrThrow(id: Long): Customer {
        val dbCustomer = customerRepository.findById(id)

        if (dbCustomer.isEmpty) {
            throw NotFoundException("Customer not found for id $id")
        }

        return dbCustomer.get()
    }
}