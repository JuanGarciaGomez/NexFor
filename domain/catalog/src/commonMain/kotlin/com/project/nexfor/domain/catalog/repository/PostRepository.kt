package com.project.nexfor.domain.catalog.repository

import com.project.nexfor.domain.catalog.model.Post

/**
 * Contract for fetching post data.
 */
interface PostRepository {
    /**
     * Fetches a post from the data source.
     */
    suspend fun getPost(): Post
}
