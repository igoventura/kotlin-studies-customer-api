package me.igoventura.kotlin_studies_customer_api.model


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "customers")
data class Customer(
    @Id
    val id: Long? = null,
    var name: String,
    var email: String
)
