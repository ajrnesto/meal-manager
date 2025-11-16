package com.example.mealmanager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mealmanager.screens.LoginScreen
import com.example.mealmanager.viewmodels.AuthViewModel
import com.example.mealmanager.viewmodels.TestViewModel
import com.example.mealmanager.viewmodels.testViewModelFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mealmanager.composeapp.generated.resources.Res
import mealmanager.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val authViewModelFactory = viewModelFactory {
        initializer {
            AuthViewModel(
                supabaseUrl = BuildConfig.SUPABASE_URL,
                supabaseKey = BuildConfig.SUPABASE_KEY
            )
        }
    }

    val authViewModel: AuthViewModel = viewModel(
        factory = authViewModelFactory
    )

    MaterialTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                LoginScreen(authViewModel)
            }
        }
    }
}