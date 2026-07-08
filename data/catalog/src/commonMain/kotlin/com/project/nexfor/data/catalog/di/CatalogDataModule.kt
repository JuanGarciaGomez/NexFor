package com.project.nexfor.data.catalog.di

import com.project.nexfor.data.catalog.remote.PostRemoteDataSource
import com.project.nexfor.data.catalog.repository.PostRepositoryImpl
import com.project.nexfor.domain.catalog.repository.PostRepository
import com.project.nexfor.domain.catalog.usecase.GetPostUseCase
import org.koin.dsl.module

/**
 * Koin module for catalog data layer.
 */
val catalogDataModule = module {
    single { PostRemoteDataSource(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    factory { GetPostUseCase(get()) }
}
