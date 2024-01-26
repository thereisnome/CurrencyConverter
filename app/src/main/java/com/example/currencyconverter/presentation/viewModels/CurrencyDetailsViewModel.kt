package com.example.currencyconverter.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.domain.usecases.GetCurrencyByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    private val getCurrencyByIdUseCase: GetCurrencyByIdUseCase
) : ViewModel() {

    private val _resultStateFlow = MutableStateFlow<CurrencyEntity?>(null)
    val resultStateFlow = _resultStateFlow.asStateFlow()

    fun getCurrencyById(id: String, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCurrencyByIdUseCase(id, date)
            withContext(Dispatchers.Main){
                _resultStateFlow.value = result
            }
        }
    }
}