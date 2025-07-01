package me.igoventura.kotlin_studies_customer_api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import me.igoventura.kotlin_studies_customer_api.dto.CustomerRequest
import me.igoventura.kotlin_studies_customer_api.dto.CustomerResponse
import me.igoventura.kotlin_studies_customer_api.dto.PageResponse
import me.igoventura.kotlin_studies_customer_api.exception.ErrorResponse
import me.igoventura.kotlin_studies_customer_api.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    @Operation(
        summary = "Get all customers (paginated)",
        description = "Returns a paginated list of all customers."
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
    suspend fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PageResponse<CustomerResponse>> {
        return ResponseEntity.ok().body(customerService.getAllByPage(page, size))
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
    suspend fun getById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok().body(customerService.getById(id))
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
    suspend fun create(@Valid @RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
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
    suspend fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: CustomerRequest
    ): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok().body(customerService.update(id, request))
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
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}