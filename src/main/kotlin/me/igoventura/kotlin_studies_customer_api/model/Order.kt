package me.igoventura.kotlin_studies_customer_api.model

import me.igoventura.kotlin_studies_customer_api.dto.OrderResponse
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("orders")
data class Order(
    @Id
    val id: Long? = null,

    val customerId: Long,

    val productName: String,

    val amount: Double
)