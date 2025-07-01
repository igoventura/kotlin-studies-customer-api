package me.igoventura.customer_api_spring_boot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerApiSpringBootApplication

fun main(args: Array<String>) {
	runApplication<CustomerApiSpringBootApplication>(*args)
}
