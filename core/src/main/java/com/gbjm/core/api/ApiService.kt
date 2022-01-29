package com.gbjm.core.api

import com.gbjm.core.model.entity.UserDataEntity
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class ResponseItems<T>(
    @SerializedName("results") val results: List<T>
)

interface ApiService {
    @GET("api/?seed=${SEED_VALUE}")
    suspend fun getUsers(@Query("results") pageSize: Int, @Query("page") page: Int): ResponseItems<UserDataEntity>
}