package com.project.nexfor.feature.inventory.di

import com.project.nexfor.feature.inventory.viewmodel.PostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for inventory feature.
 */
val inventoryFeatureModule = module {
    viewModelOf(::PostViewModel)
}
