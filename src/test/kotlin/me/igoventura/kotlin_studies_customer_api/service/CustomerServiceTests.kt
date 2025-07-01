package me.igoventura.kotlin_studies_customer_api.service

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import me.igoventura.kotlin_studies_customer_api.dto.CustomerRequest
import me.igoventura.kotlin_studies_customer_api.model.Customer
import me.igoventura.kotlin_studies_customer_api.repository.CustomerRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CustomerServiceTests {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun `should return customer when getById is called with existing id`() = runTest {
        val customerId = 1L
        val mockCustomer = Customer(id = customerId, name = "Test User", email = "test@user.com")

        coEvery { customerRepository.findById(customerId) } returns mockCustomer

        val result = customerService.getById(customerId)

        assertEquals(customerId, result.id)
        assertEquals("Test User", result.name)

        coVerify (exactly = 1) { customerRepository.findById(customerId) }
    }

    @Test
    fun `should create and return customer`() = runTest {
        val request = CustomerRequest(name = "New User", email = "new@user.com")
        val savedCustomer = Customer(id = 2L, name = "New User", email = "new@user.com")

        coEvery { customerRepository.save(any()) } returns savedCustomer

        val result = customerService.create(request)

        assertEquals(savedCustomer.id, result.id)
        assertEquals(request.name, result.name)

        coVerify(exactly = 1) { customerRepository.save(any()) }
    }
}