package com.practicum.list.core.data.remote.api

import com.practicum.list.core.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getPopularProducts(): List<ProductDto>
}
