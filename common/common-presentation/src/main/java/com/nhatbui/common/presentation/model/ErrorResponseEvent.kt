package com.nhatbui.common.presentation.model

import com.nhatbui.common.presentation.PresentationEvent

data class ErrorResponseEvent(
    val cause: Throwable?
) : PresentationEvent
