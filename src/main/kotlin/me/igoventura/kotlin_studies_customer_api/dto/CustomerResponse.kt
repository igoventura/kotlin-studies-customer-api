package me.igoventura.kotlin_studies_customer_api.dto

import me.igoventura.kotlin_studies_customer_api.model.Customer

data class CustomerResponse(
    val id: Long,
    val name: String,
    val email: String
)

fun Customer.toResponse(): CustomerResponse {
    val nonNullId = requireNotNull(id) { "Customer ID must not be null to create a response" }
    return CustomerResponse(id = nonNullId, name = name, email = email)
}