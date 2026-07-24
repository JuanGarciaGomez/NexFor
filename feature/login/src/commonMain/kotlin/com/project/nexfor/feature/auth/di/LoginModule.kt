package com.project.nexfor.feature.auth.di

import com.project.nexfor.feature.auth.ui.LoginViewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginViewModel() }
}
