package me.igoventura.kotlin_studies_customer_api.repository

import kotlinx.coroutines.flow.Flow
import me.igoventura.kotlin_studies_customer_api.model.Order
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: PageableRepository<Order, Long> {
    suspend fun findByCustomerId(customerId: Long): Flow<Order>
}