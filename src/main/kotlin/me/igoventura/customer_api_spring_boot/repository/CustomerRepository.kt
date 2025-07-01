package me.igoventura.customer_api_spring_boot.repository

import me.igoventura.customer_api_spring_boot.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : PagingAndSortingRepository<Customer, Long>, CrudRepository<Customer, Long> {}