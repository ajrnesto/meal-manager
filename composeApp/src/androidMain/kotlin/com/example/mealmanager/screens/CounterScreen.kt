package com.example.mealmanager.screens

import android.widget.CheckBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mealmanager.viewmodels.TestViewModel
import com.example.mealmanager.viewmodels.testViewModelFactory

@Composable
fun CounterScreen() {
    val testViewModel: TestViewModel = viewModel(
        factory = testViewModelFactory
    )
    val counter by testViewModel.counter.collectAsState()
    val autoUpdate by testViewModel.autoUpdate.collectAsState()

    var textFieldValue by remember { mutableStateOf(TextFieldValue("0")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textFieldValue,
            onValueChange = { newValue ->
                if (newValue.text.isEmpty()) {
                    textFieldValue = TextFieldValue(
                        text = "0",
                        selection = TextRange(1)
                    )

                }
                else if (newValue.text.length < 6) {
                    textFieldValue =
                        if (newValue.text.first() == '0' && newValue.text.length > 1) {
                            TextFieldValue(
                                text = newValue.text.removeRange(0, 1),
                                selection = TextRange(newValue.text.length - 1))
                        }
                        else {
                            newValue
                        }
                }

                if (autoUpdate) {
                    testViewModel.setCounter(textFieldValue.text.toInt())
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = autoUpdate,
                onCheckedChange = {
                    testViewModel.setAutoUpdate(it)
                }
            )
            Text(
                text = "Enable Auto-update?"
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                if (textFieldValue.text.isNotEmpty()) {
                    testViewModel.setCounter(textFieldValue.text.toInt())
                }
            }
        ) {
            Text(
                text = "Update"
            )
        }
    }
}