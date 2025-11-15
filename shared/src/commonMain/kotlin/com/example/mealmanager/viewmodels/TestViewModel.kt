package com.example.mealmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

val testViewModelFactory = viewModelFactory {
    initializer {
        TestViewModel(
            // pass any dependencies here
        )
    }
}

class TestViewModel : ViewModel() {
    private val _counter = MutableStateFlow(90)
    val counter = _counter.asStateFlow()

    fun setCounter(value: Int) {
        viewModelScope.launch {
            _counter.value = value
        }
    }

    private val _autoUpdate = MutableStateFlow(true)
    val autoUpdate = _autoUpdate.asStateFlow()

    fun setAutoUpdate(value: Boolean) {
        viewModelScope.launch {
            _autoUpdate.value = value
        }
    }
}