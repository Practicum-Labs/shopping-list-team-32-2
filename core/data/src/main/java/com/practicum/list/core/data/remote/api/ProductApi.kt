package com.practicum.list.core.data.remote.api

import retrofit2.http.GET
import com.practicum.list.core.data.remote.dto.ProductDto

interface ProductApi {

    @GET("products")
    suspend fun getPopularProducts(): List<ProductDto>
}
