package com.project.nexfor.data.catalog.repository

import com.project.nexfor.data.catalog.remote.PostRemoteDataSource
import com.project.nexfor.domain.catalog.model.Post
import com.project.nexfor.domain.catalog.repository.PostRepository

/**
 * Implementation of [PostRepository].
 */
class PostRepositoryImpl(
    private val dataSource: PostRemoteDataSource
) : PostRepository {
    override suspend fun getPost(): Post = dataSource.getPost()
}
