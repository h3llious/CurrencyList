package com.nhatbui.currencylist.di

import com.google.gson.Gson
import com.nhatbui.common.domain.CoroutineContextProvider
import com.nhatbui.common.domain.UseCaseExecutor
import com.nhatbui.common.domain.UseCaseExecutorProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    fun providesCoroutineContextProvider(): CoroutineContextProvider =
        CoroutineContextProvider.Default

    @Provides
    fun providesUseCaseExecutorProvider(): UseCaseExecutorProvider =
        { coroutineScope -> UseCaseExecutor(coroutineScope) }

    @Provides
    fun providesGson(): Gson = Gson()
}
