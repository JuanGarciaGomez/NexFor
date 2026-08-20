package com.project.nexfor.data.login.di

import com.project.nexfor.data.login.remote.LoginApiService
import com.project.nexfor.data.login.repository.LoginRepositoryImpl
import com.project.nexfor.domain.login.repository.LoginRepository
import com.project.nexfor.domain.login.usecase.LoginUseCase
import org.koin.dsl.module

val loginDataModule = module {
    single { LoginApiService(get()) }
    single<LoginRepository> {
        LoginRepositoryImpl(
            loginApiService = get(),
            sessionManager = get()
        )
    }
    factory { LoginUseCase(get()) }
}