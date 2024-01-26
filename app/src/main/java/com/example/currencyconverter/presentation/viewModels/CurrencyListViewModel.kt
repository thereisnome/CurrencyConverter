package com.example.currencyconverter.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecases.GetCurrencyListUseCase
import com.example.currencyconverter.presentation.Error
import com.example.currencyconverter.presentation.Loading
import com.example.currencyconverter.presentation.State
import com.example.currencyconverter.presentation.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(Loading)
    val state = _state.asStateFlow()

    fun getCurrencyList(date: String) {
        _state.value = Loading
        viewModelScope.launch {
            getCurrencyListUseCase(date).flowOn(Dispatchers.IO).collect { currencyList ->
                if (currencyList.isEmpty()) {
                    _state.emit(Error)
                } else {
                    _state.emit(Success(currencyList))
                }
            }
        }
    }
}