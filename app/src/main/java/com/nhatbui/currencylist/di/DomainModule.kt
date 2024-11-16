package com.nhatbui.currencylist.di

import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.currency.domain.ExampleUseCase
import com.nhatbui.currency.domain.ExampleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun providesExampleUseCase(
        coroutineContextProvider: CoroutineContextProvider
    ): ExampleUseCase = ExampleUseCaseImpl(coroutineContextProvider)
}
