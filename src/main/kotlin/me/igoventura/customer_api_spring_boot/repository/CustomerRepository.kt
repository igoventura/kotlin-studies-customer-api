package me.igoventura.customer_api_spring_boot.repository

import me.igoventura.customer_api_spring_boot.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {}