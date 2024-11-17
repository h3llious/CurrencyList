package com.nhatbui.common.domain

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
    val default: CoroutineContext

    object Default : CoroutineContextProvider {
        override val main: CoroutineContext = Dispatchers.Main
        override val io: CoroutineContext = Dispatchers.IO
        override val default: CoroutineContext = Dispatchers.Default
    }
}
