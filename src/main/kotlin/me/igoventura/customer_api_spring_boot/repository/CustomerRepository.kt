package me.igoventura.customer_api_spring_boot.repository

import me.igoventura.customer_api_spring_boot.model.Customer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CoroutineCrudRepository<Customer, Long> {}