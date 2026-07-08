package com.project.nexfor.domain.catalog.usecase

import com.project.nexfor.domain.catalog.model.Post
import com.project.nexfor.domain.catalog.repository.PostRepository

/**
 * Use case to retrieve a single post.
 */
class GetPostUseCase(
    private val repository: PostRepository
) {
    /**
     * Executes the use case.
     */
    suspend operator fun invoke(): Post = repository.getPost()
}
