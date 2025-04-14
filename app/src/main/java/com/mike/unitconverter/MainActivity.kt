package com.mike.unitconverter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mike.unitconverter.ui.theme.UnitConverterTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private var value2: String = ""
    private var from2: String = "Centimeters"
    private var to2: String = "Meters"
    private var result2: String = value2

    fun onValueChange(value: String, from: String, to: String, result: String) {
        Log.d("TAG 2", "Value: $value, From: $from, To: $to, Result: $result")

        value2 = value
        from2 = from
        to2 = to
        result2 = result
        Log.d("TAG 3", "Value: $value2, From: $from2, To: $to2, Result: $result2")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG 4", "Value: $value2, From: $from2, To: $to2, Result: $result2")

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        value2,
                        from2,
                        to2,
                        result2,
                        onValueChange = { value, from, to, result ->
                            onValueChange(value, from, to, result)
                        },
                        modifier = Modifier.padding(
                            innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Content(value2: String, from2: String, to2: String, result2: String, onValueChange: (String, String, String, String) -> Unit, modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf(value2) }
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var from by remember { mutableStateOf(from2) }
    var to by remember { mutableStateOf(to2) }
    var result by remember { mutableStateOf(result2) }
    var context = LocalContext.current

    fun convert(value: String): String {
        return when (from) {
            "Centimeters" -> {
                when (to) {
                    "Meters" -> (value.toDoubleOrNull()?.let { it / 100 }).toString()
                    "Feet" -> (value.toDoubleOrNull()?.let { it / 30.48 }).toString()
                    "Milimiters" -> (value.toDoubleOrNull()?.let { it * 10 }).toString()
                    else -> value
                }
            }
            "Meters" -> {
                when (to) {
                    "Centimeters" -> (value.toDoubleOrNull()?.let { it * 100 }).toString()
                    "Feet" -> (value.toDoubleOrNull()?.let { it * 3.28084 }).toString()
                    "Milimiters" -> (value.toDoubleOrNull()?.let { it * 1000 }).toString()
                    else -> value
                }
            }
            "Feet" -> {
                when (to) {
                    "Centimeters" -> (value.toDoubleOrNull()?.let { it * 30.48 }).toString()
                    "Meters" -> (value.toDoubleOrNull()?.let { it / 3.28084 }).toString()
                    "Milimiters" -> (value.toDoubleOrNull()?.let { it * 304.8 }).toString()
                    else -> value
                }
            }
            "Milimiters" -> {
                when (to) {
                    "Centimeters" -> (value.toDoubleOrNull()?.let { it / 10 }).toString()
                    "Meters" -> (value.toDoubleOrNull()?.let { it / 1000 }).toString()
                    "Feet" -> (value.toDoubleOrNull()?.let { it / 304.8 }).toString()
                    else -> value
                }
            }

            else -> value
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
        .padding(16.dp).border(BorderStroke(1.dp, color = androidx.compose.ui.graphics.Color.Gray), shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            modifier = modifier.align(Alignment.CenterHorizontally),
            fontSize = 24.sp,

        )
        TextInput(
            value = value,
            onValueChange = { value = it },
            onCallback = {
                if (value.isNotEmpty()) {
                    result = convert(value)
                    onValueChange(value, from, to, result)
                    Log.d("TAG", "Value: $value2, From: $from2, To: $to2, Result: $result2")
                } else {
                    result = (0.0).toString()
                }

            },
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            ButtonsOption(
                expanded = expanded,
                onExpand = { it ->
                    if (it == null) {
                        expanded = !expanded
                    } else {
                        expanded = it
                    }

                },
                measure = from,
                onChangeMeasure = { from = it },
                onCallback = {
                    if (value.isNotEmpty()) {
                        result = convert(value)
                        onValueChange(value, from, to, result)
                    } else {
                        result = (0.0).toString()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ButtonsOption(
                expanded = expanded2,
                onExpand = { it ->
                    if (it == null) {
                        expanded2 = !expanded2
                    } else {
                        expanded2 = it
                    }

                },
                measure = to,
                onChangeMeasure = { to = it },
                onCallback = {
                    if (value.isNotEmpty()) {
                        result = convert(value)
                        onValueChange(value, from, to, result)
                    } else {
                        result = (0.0).toString()
                    }

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )

        }
        Text(
            text = "Result is: ${result} $to",
            modifier = Modifier
                .padding(16.dp).align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
        )

    }
}

@Composable
fun TextInput(value: String = "", onValueChange: (String) -> Unit, onCallback: () -> Unit, modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .focusRequester(focusRequester),
        value = value,
        onValueChange = { it -> onValueChange.invoke(it)
                        if (it.isNotEmpty()) {
                onCallback.invoke()
            }
        },
        placeholder = { Text("Enter value") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusRequester.freeFocus()
                focusManager.clearFocus()
            }
        ),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon") },
        shape = RoundedCornerShape(8.dp)
    )

}

@Composable
fun ButtonsOption(expanded: Boolean = false, onExpand: (boolean: Boolean?) -> Unit, measure: String, onChangeMeasure: (String) -> Unit, onCallback: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Button(onClick = { onExpand(!expanded) }) {
            Text(text = measure)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More options",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpand(false) }
        ) {

            DropdownMenuItem(
                text = { Text("Centimeters") },
                onClick = {
                    onChangeMeasure.invoke("Centimeters")
                    onExpand(false)
                    onCallback.invoke()
                }
            )
            DropdownMenuItem(
                text = { Text("Meters") },
                onClick = {
                    onChangeMeasure.invoke("Meters")
                    onExpand(false)
                    onCallback.invoke()
                }
            )
            DropdownMenuItem(
                text = { Text("Feet") },
                onClick = {
                    onChangeMeasure.invoke("Feet")
                    onExpand(false)
                    onCallback.invoke()
                }
            )
            DropdownMenuItem(
                text = { Text("Milimiters") },
                onClick = {
                    onChangeMeasure.invoke("Milimiters")
                    onExpand(false)
                    onCallback.invoke()
                }
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {
        Content(
            modifier = Modifier.padding(16.dp)
        )
    }
}*/