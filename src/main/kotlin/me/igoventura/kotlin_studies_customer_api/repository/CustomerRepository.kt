package me.igoventura.kotlin_studies_customer_api.repository

import me.igoventura.kotlin_studies_customer_api.model.Customer
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : PageableRepository<Customer, Long> {}