package com.example.mealmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmanager.BuildConfig
import com.example.mealmanager.api.client.SupabaseAuthClient
import com.example.mealmanager.api.models.AuthResponse
import com.example.mealmanager.api.repository.ApiResult
import com.example.mealmanager.api.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authClient = SupabaseAuthClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY
    )
    private val authRepository = AuthRepository(authClient)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authRepository.signIn(email, password)) {
                is ApiResult.Success -> {
                    println("Supabase AuthViewModel - signIn: Success ${result.data}")
                    _authState.value = AuthState.SignInSuccess(result.data)
                }
                is ApiResult.Error -> {
                    println("Supabase AuthViewModel - signIn: Success ${result.message}")
                    _authState.value = AuthState.Error(result.message)
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authRepository.signUp(email, password)) {
                is ApiResult.Success -> {
                    val response = result.data
                    if (response.accessToken != null) {
                        // Email confirmation disabled
                        _authState.value = AuthState.SignUpSuccess(response, needsConfirmation = false)
                    } else {
                        // Email confirmation enabled
                        _authState.value = AuthState.SignUpSuccess(response, needsConfirmation = true)
                    }
                }
                is ApiResult.Error -> {
                    _authState.value = AuthState.Error(result.message)
                }
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class SignInSuccess(val authResponse: AuthResponse) : AuthState()
    data class SignUpSuccess(val response: AuthResponse, val needsConfirmation: Boolean) : AuthState()
    data class Error(val message: String) : AuthState()
}