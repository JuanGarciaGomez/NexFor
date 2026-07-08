package com.project.nexfor.data.catalog.remote

import com.project.nexfor.domain.catalog.model.Post
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Post.
 * Don't forget SerializableName
 */
@Serializable
data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

/**
 * Maps [PostDto] to domain [Post].
 */
fun PostDto.toDomain() = Post(
    id = id,
    title = title,
    body = body
)
