package com.example.ecomapp.di

import com.example.ecomapp.data.repository.FirestoreProductRepository
import com.example.ecomapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductRepository(impl: FirestoreProductRepository): ProductRepository
}

/*@InstallIn(SingletonComponent::class)
@Module*/

