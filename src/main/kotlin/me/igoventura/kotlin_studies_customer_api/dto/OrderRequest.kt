package me.igoventura.kotlin_studies_customer_api.dto

import jakarta.validation.constraints.NotEmpty
import me.igoventura.kotlin_studies_customer_api.model.Order

data class OrderRequest(
    @field:NotEmpty
    val productName: String,
    @field:NotEmpty
    val amount: Double
)

fun OrderRequest.toModel(customerId: Long): Order {
    return Order(
        customerId = customerId,
        productName = productName,
        amount = amount
    )
}