package me.igoventura.customer_api_spring_boot.dto

import me.igoventura.customer_api_spring_boot.model.Customer

data class CustomerResponse(
    val id: Long,
    val name: String,
    val email: String
)

fun Customer.toResponse(): CustomerResponse {
    val nonNullId = requireNotNull(id) { "Customer ID must not be null to create a response" }
    return CustomerResponse(id = nonNullId, name = name, email = email)
}