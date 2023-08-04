package com.example.ecomapp.di

import com.example.ecomapp.data.repository.FirestoreProductRepository
import com.example.ecomapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductRepository(impl: FirestoreProductRepository): ProductRepository
}