package me.igoventura.customer_api_spring_boot.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import me.igoventura.customer_api_spring_boot.dto.CustomerRequest
import me.igoventura.customer_api_spring_boot.dto.CustomerResponse
import me.igoventura.customer_api_spring_boot.exception.ErrorResponse
import me.igoventura.customer_api_spring_boot.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    @Operation(
        summary = "Get all customers",
        description = "Returns all customers."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customers found successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Array<CustomerResponse>::class)
                    )
                ]
            )
        ]
    )
    @GetMapping
    fun getAll(): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.getAll()
        return ResponseEntity.ok().body(customers)
    }

    @Operation(
        summary = "Get a customer by their ID",
        description = "Returns a single customer based on the unique identifier."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer found successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CustomerResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer = customerService.getById(id)
        return ResponseEntity.ok().body(customer)
    }

    @Operation(
        summary = "Create a customer",
        description = "Create a customer given the request and return it."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Customer created successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CustomerResponse::class)
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun create(@Valid @RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val createdCustomer = customerService.create(request)

        return ResponseEntity
            .created(URI.create("/api/v1/customers/${createdCustomer.id}"))
            .body(createdCustomer)
    }

    @Operation(
        summary = "Update a customer by their ID",
        description = "Updates the customer based on the unique identifier."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CustomerResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val updatedCustomer = customerService.update(id, request)

        return ResponseEntity.ok().body(updatedCustomer)
    }

    @Operation(
        summary = "Delete a customer by their ID",
        description = "Deletes the customer based on the unique identifier."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Customer deleted successfully"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}