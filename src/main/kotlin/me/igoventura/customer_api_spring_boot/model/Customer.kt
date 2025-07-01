package me.igoventura.customer_api_spring_boot.model


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "customers")
data class Customer(
    @Id
    val id: Long? = null,
    var name: String,
    var email: String
)
