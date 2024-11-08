package com.example.brofin.presentation.test

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.domain.models.User
import com.example.brofin.utils.UserAlreadyExistsException
import com.example.brofin.utils.toFormattedDate
import com.example.brofin.utils.toIndonesianCurrency

@Composable
fun UserTestingScreen(
    viewModel: UserViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val userState by viewModel.userState.collectAsState() // Collect the user state

    val insertStatus by viewModel.userInsertStatus.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(insertStatus) {
        insertStatus.onSuccess {
            Toast.makeText(context, "User inserted successfully!", Toast.LENGTH_SHORT).show()
        }.onFailure { exception ->
            if (exception is UserAlreadyExistsException) {
                Toast.makeText(context, "User already exists!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error occurred while inserting user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display the current user information
            Text(text = "Current User: ${userState?.name ?: "No user"}")
            Text(text = "Balance: ${userState?.currentBalance?.toIndonesianCurrency() ?: "No balance"}")
            Text(text = "CreatedAt: ${userState?.createdAt?.toFormattedDate() ?: "Note Date"}")
            // Input fields to create or update a user
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = balance,
                onValueChange = { balance = it },
                label = { Text("Balance") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Insert User Button
                Button(onClick = {
                    val user = User(
                        name = name,
                        email = email,
                        phoneNumber = phoneNumber,
                        createdAt = System.currentTimeMillis(),
                        currentBalance = balance.toDoubleOrNull() ?: 0.0
                    )
                    viewModel.insertUser(user)

                }) {
                    Text("Insert User")
                }

                // Update User Button
                Button(onClick = {
                    viewModel.updateUser(
                        name = name,
                        email = email,
                        phoneNumber = phoneNumber,
                        balance = balance.toDoubleOrNull()
                    )
                }) {
                    Text("Update User")
                }
            }

            // Delete User Button
            Button(
                onClick = { viewModel.deleteUser() },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Delete User", color = Color.White)
            }
        }
    }
}