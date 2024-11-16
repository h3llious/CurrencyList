package com.nhatbui.currency.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(

): ViewModel() {
    // TODO temp test Hilt impl
    val name = "Android"
}
