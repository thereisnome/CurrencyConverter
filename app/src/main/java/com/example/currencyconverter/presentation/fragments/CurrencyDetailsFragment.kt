package com.example.currencyconverter.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentCurrencyDetailsBinding
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.presentation.viewModels.CurrencyDetailsViewModel
import com.example.currencyconverter.utils.formatCurrency
import com.example.currencyconverter.utils.formatCurrencyWithoutSign
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyDetailsFragment : Fragment() {

    private var _binding: FragmentCurrencyDetailsBinding? = null

    private val binding: FragmentCurrencyDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentCurrencyDetailsBinding is null")

    val viewModel: CurrencyDetailsViewModel by viewModels()

    private val args by navArgs<CurrencyDetailsFragmentArgs>()

    private lateinit var currency: CurrencyEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrencyById(args.currencyId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.resultStateFlow.collect { currencyEntity ->
                    if (currencyEntity != null) {
                        currency = currencyEntity
                        displayResult()
                        setupTopBar()
                    }
                }
            }
        }

        setTextWatchers()
        setFocusListeners()
    }

    private fun setupTopBar() {
        with(binding.detailsTopBar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            title = currency.charCode
        }
    }

    private fun setTextWatchers() {
        with(binding) {
            etExchangeTop.doOnTextChanged { text, _, _, _ ->
                if (etExchangeTop.hasFocus() && text.toString().isNotBlank()) {
                    etExchangeBot.setText(exchangeToRub(text.toString().toDouble()))
                }
                if (etExchangeTop.hasFocus() && text.toString().isBlank()) {
                    etExchangeBot.setText("")
                }
            }

            etExchangeBot.doOnTextChanged { text, _, _, _ ->
                if (etExchangeBot.hasFocus() && text.toString().isNotBlank()) {
                    etExchangeTop.setText(exchangeToCurrency(text.toString().toDouble()))
                }
                if (etExchangeBot.hasFocus() && text.toString().isBlank()) {
                    etExchangeTop.setText("")
                }
            }
        }
    }

    private fun setFocusListeners() {
        with(binding) {
            etExchangeTop.setOnFocusChangeListener { _, _ ->
                etExchangeTop.setText("")
            }

            etExchangeBot.setOnFocusChangeListener { _, _ ->
                etExchangeBot.setText("")
            }
        }
    }

    private fun displayResult() {
        with(binding) {
            currencyName.text = currency.name
            exchangeRateValue.text = formatCurrency(currency.value)
            tilExchangeTop.hint = currency.charCode
            tilExchangeBot.hint = requireContext().getString(R.string.rub)
        }
    }

    private fun exchangeToRub(exValue: Double): String {
        return formatCurrencyWithoutSign(exValue * currency.value)
    }

    private fun exchangeToCurrency(exValue: Double): String {
        return formatCurrencyWithoutSign(exValue / currency.value)
    }
}