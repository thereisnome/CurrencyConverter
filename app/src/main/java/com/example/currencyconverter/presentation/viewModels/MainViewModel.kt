package com.example.currencyconverter.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecases.LoadDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    fun loadData(){
        viewModelScope.launch(Dispatchers.IO) { loadDataUseCase() }
    }
}