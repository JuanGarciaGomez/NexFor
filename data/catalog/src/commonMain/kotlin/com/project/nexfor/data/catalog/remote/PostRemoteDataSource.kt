package com.project.nexfor.data.catalog.remote

import com.project.nexfor.core.network.model.NetworkResult
import com.project.nexfor.core.network.util.safeApiCall
import com.project.nexfor.domain.catalog.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Remote data source for fetching posts from the API.
 */
class PostRemoteDataSource(
    private val client: HttpClient
) {
    /**
     * Fetches a post from the API.
     */
    suspend fun getPost(): Post {
        val result = safeApiCall {
            val response = client.get("https://jsonplaceholder.typicode.com/posts/1").body<PostDto>()
            NetworkResult.Success(response, "Success", 200)
        }
        
        return when (result) {
            is NetworkResult.Success -> result.data.toDomain()
            is NetworkResult.Error -> throw Exception(result.message)
        }
    }
}
