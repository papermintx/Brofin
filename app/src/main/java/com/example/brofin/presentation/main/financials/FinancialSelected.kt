package com.example.brofin.presentation.main.financials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.financials.components.MyDropDownCustom
import com.example.brofin.presentation.main.home.components.CustomTextFieldTwo
import com.example.brofin.utils.getFormattedTimeInMillis
import com.example.brofin.utils.toFormattedDate


@Composable
fun FinancialSelected(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .borderDashed(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    dashLength = 10f,
                    gapLength = 6f
                )
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            ContentBox2()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBox2() {
    var showDatePicker by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(getFormattedTimeInMillis(System.currentTimeMillis())) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Pilih Kategori") }
    val options = listOf("Mobil", "Gadget", "Motor", "Luxury Brand", "Games")

    // Daftar nama item dan nilai defaultnya
    var listNamaItem = remember {
        mutableStateListOf("Jumlah Target Uang", "Estimasi")
    }

    var listNilai = remember {
        mutableStateListOf(
            "0",
            date.toFormattedDate()
        )
    }

    // Memperbarui combined setiap kali ada perubahan di listNilai
    val combined = listNamaItem.zip(listNilai) { nama, nilai ->
        Pair(nama, nilai)
    }

    Column(
        Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = selectedOptionText,
                onValueChange = { selectedOptionText = it },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(type = MenuAnchorType.PrimaryEditable)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                        text = {
                            Text(
                                text = selectionOption,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Menampilkan listNamaItem dan listNilai dalam Row
        combined.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    RowNamaItem(pair.first)
                }

                Column(modifier = Modifier.weight(0.1f)) {
                    RowTitik2()
                }

                Column(modifier = Modifier.weight(0.6f)) {
                    RowItem(pair.second)
                }

                Column(modifier = Modifier.weight(0.2f)) {
                    RowEditButton {
                        if (index == 1) { // Jika ini baris estimasi, maka tampilkan date picker
                            showDatePicker = true
                        }
                    }
                }
            }
        }

        // Menampilkan DatePicker jika showDatePicker true
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                // Update date dengan nilai baru yang dipilih
                                listNilai[1] = it.toFormattedDate()
                            }
                            showDatePicker = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Pilih")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text("Batal")
                    }
                },
                content = {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true
                    )
                }
            )
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FinancialSelected() {
//
//    val categories = listOf("Mobil", "Gadget", "Motor", "Luxury Brand", "Games")
//    var selectedValue by remember { mutableStateOf("Pilih Kategori") }
//    var textValue by remember { mutableStateOf("") }
//    var showDatePicker by remember { mutableStateOf(false) }
//    var date by remember { mutableLongStateOf(getFormattedTimeInMillis(System.currentTimeMillis())) }
//    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
//    var isDatePickerVisible by remember { mutableStateOf(false) }
//    val scrrollState = rememberScrollState()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ){
//        Column(
//            Modifier.verticalScroll(state = scrrollState)
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//            MyDropDownCustom(
//                selectedValue = selectedValue,
//                options = categories,
//                onValueChangedEvent = {
//                    selectedValue = it
//                },
//                color = MaterialTheme.colorScheme.surface
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            CustomTextFieldTwo(
//                label = "Jumlah Target Uang",
//                text = textValue,
//                onTextChange = {
//                    textValue = it
//                },
//                validate = {
//                    if (it.isEmpty()) {
//                        "Jumlah Target Uang tidak boleh kosong"
//                    } else if (it.toDoubleOrNull() == null) {
//                        "Jumlah Target Uang harus berupa angka"
//                    } else {
//                        ""
//                    }
//                },
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number
//                ),
//                singleLine = true,
//                maxLines = 1
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedButton(
//                onClick = {
//                    showDatePicker = true
//                },
//                shape = RoundedCornerShape(15.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    "Estimasi",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier
//                        .padding(8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//
//                },
//                shape = RoundedCornerShape(15.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    "Hitung Prediksi",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier
//                        .padding(8.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Card(
//                modifier = Modifier
//                    .borderDashed(
//                        width = 1.dp,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        dashLength = 10f,
//                        gapLength = 6f
//                    )
//                    .padding(5.dp)
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .shadow(
//                        elevation = 0.dp,
//                        shape = RoundedCornerShape(16.dp)
//                    )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                ) {
//                    Text(
//                        text = "Hasil prediksi yang sesuai dengan input :\nElden Ring\nRed Dead Redemption 2\nDevil May Cry 5\nResident Evil 4 Remake\nFar Cry 3",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//            }
//
//        }
//
//        if (showDatePicker) {
//            DatePickerDialog(
//                onDismissRequest = { showDatePicker = false },
//                confirmButton = {
//                    Button(
//                        onClick = {
//                            datePickerState.selectedDateMillis?.let {
//                                date = it
//                            }
//                            showDatePicker = false
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.primary,
//                            contentColor = MaterialTheme.colorScheme.onPrimary
//                        )
//                    ) {
//                        Text("Pilih")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = {
//                        isDatePickerVisible = false
//                        showDatePicker = false
//                    }) {
//                        Text("Batal")
//                    }
//                },
//                content = {
//                    DatePicker(
//                        state = datePickerState,
//                        showModeToggle = true
//                    )
//                }
//            )
//
//        }
//    }
//
//}

fun Modifier.borderDashed(
    width: Dp,
    color: Color,
    dashLength: Float = 10f,
    gapLength: Float = 6f
): Modifier = this.then(
    Modifier.drawBehind {
        val size = this.size
        val cornerRadius = 16.dp.toPx() // Mengonversi radius ke px

        val paint = Paint().apply {
            this.color = color
            this.style = Stroke
            this.strokeWidth = width.toPx() // Mengonversi Dp ke Px untuk lebar border
            this.pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength)) // Border putus-putus
        }

        // Membuat path dengan rounded corners
        val path = Path().apply {
            // Menambahkan rounded rect (segmen dengan radius sudut)
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )
        }

        // Menggambar path (border putus-putus) ke canvas
        drawIntoCanvas { canvas ->
            canvas.drawPath(path, paint) // Menggambar path menggunakan canvas
        }
    }
)
