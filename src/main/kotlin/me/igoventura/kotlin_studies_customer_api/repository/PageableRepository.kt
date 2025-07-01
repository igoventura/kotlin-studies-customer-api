package me.igoventura.kotlin_studies_customer_api.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@NoRepositoryBean
interface PageableRepository<T, ID>: CoroutineCrudRepository<T, ID> {
    fun findAllBy(pageable: Pageable): Flow<T>
}