package me.igoventura.kotlin_studies_customer_api.controller

import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import me.igoventura.kotlin_studies_customer_api.dto.OrderRequest
import me.igoventura.kotlin_studies_customer_api.dto.OrderResponse
import me.igoventura.kotlin_studies_customer_api.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/customers/{customerId}/orders")
class OrderController(
    private val orderService: OrderService
){
    @PostMapping
    suspend fun create(
        @RequestParam customerId: Long,
        @Valid @RequestBody request: OrderRequest
    ): ResponseEntity<OrderResponse> {
        val createdOrder = orderService.create(customerId, request)
        return ResponseEntity
            .created(URI.create("/api/v1/customers/${createdOrder.customerId}/orders"))
            .body(createdOrder)
    }

    @GetMapping
    suspend fun getAllByCustomer(@RequestParam customerId: Long): ResponseEntity<Flow<OrderResponse>> {
        return ResponseEntity.ok(orderService.getAllByCustomerId(customerId))
    }
}