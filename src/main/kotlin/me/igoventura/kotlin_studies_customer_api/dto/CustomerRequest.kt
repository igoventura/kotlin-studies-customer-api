package me.igoventura.kotlin_studies_customer_api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import me.igoventura.kotlin_studies_customer_api.model.Customer

data class CustomerRequest(
    @field:NotBlank(message = "Customer name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Customer email cannot be blank")
    @field:Email(message = "Must be a valid email format")
    val email: String
)

fun CustomerRequest.toModel(): Customer {
    return Customer(name = name, email = email)
}