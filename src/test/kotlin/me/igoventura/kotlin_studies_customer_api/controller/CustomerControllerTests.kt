package me.igoventura.kotlin_studies_customer_api.controller

import kotlinx.coroutines.runBlocking
import me.igoventura.kotlin_studies_customer_api.dto.CustomerRequest
import me.igoventura.kotlin_studies_customer_api.dto.CustomerResponse
import me.igoventura.kotlin_studies_customer_api.model.Customer
import me.igoventura.kotlin_studies_customer_api.repository.CustomerRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CustomerControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup(): Unit = runBlocking {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return customer when GET by id is called with existing customer`() {
        val savedCustomer = runBlocking {
            customerRepository.save(Customer(name = "Test User", email = "test@user.com"))
        }

        webTestClient.get().uri("/api/v1/customers/${savedCustomer.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerResponse::class.java)
            .value { response ->
                assertEquals(savedCustomer.id, response.id)
                assertEquals(savedCustomer.name, response.name)
            }
    }

    @Test
    fun `should create a new customer when POST is called with valid data`() {
        val request = CustomerRequest(name = "New Customer", email = "new@customer.com")

        webTestClient.post().uri("/api/v1/customers")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CustomerResponse::class.java)
            .value { response ->
                assertNotNull(response.id)
                assertEquals(request.name, response.name)
                assertEquals(request.email, response.email)
            }
    }
}