package com.nhatbui.currency.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.ui.di.NavHostDependencies
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {
    @Inject
    lateinit var navHostDependencies: NavHostDependencies

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            CurrencyTheme(
                dynamicColor = false
            ) {
                FeatureContent(navHostDependencies)
            }
        }
    }
}
