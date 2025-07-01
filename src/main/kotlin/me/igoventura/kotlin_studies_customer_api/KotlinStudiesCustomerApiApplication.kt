package me.igoventura.kotlin_studies_customer_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerApiSpringBootApplication

fun main(args: Array<String>) {
	runApplication<CustomerApiSpringBootApplication>(*args)
}
