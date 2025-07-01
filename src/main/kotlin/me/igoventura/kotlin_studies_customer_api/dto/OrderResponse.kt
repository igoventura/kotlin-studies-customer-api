package me.igoventura.kotlin_studies_customer_api.dto

import me.igoventura.kotlin_studies_customer_api.model.Order

data class OrderResponse(
    val id: Long,

    val customerId: Long,

    val productName: String,

    val amount: Double
)

fun Order.toResponse(): OrderResponse {
    val nonNullId = requireNotNull(id) { "Order ID must not be null to create a response" }
    return OrderResponse(
        id = nonNullId,
        customerId = customerId,
        productName = productName,
        amount = amount
    )
}