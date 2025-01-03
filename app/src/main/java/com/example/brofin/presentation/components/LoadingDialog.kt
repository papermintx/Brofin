package com.example.brofin.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.brofin.R

@Composable
fun LoadingDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animate))


    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        )

        AlertDialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        speed = 0.8f,
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Mohon tunggu...", style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {}
        )
    }
}
