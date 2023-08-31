package com.example.jetnote.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnote.R
import com.example.jetnote.ui.theme.JetNoteTheme

@Composable
fun NoteScreen() {
    var title by remember {
        mutableStateOf("")
    }
    var note by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        TopAppBar(
            backgroundColor = Color.LightGray
        ) {
            Text(text = stringResource(id = R.string.app_name))
        }
        TopFields(
            title = title,
            onTitleChange = { input ->
                if (input.all { it.isLetter() || it.isWhitespace() }) title = input
            },
            note = note,
            onNoteChange = { input ->
                if (input.all { it.isLetter() || it.isWhitespace() }) note = input
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopFields(
    title: String,
    onTitleChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = "Title") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = note,
            onValueChange = onNoteChange,
            label = { Text(text = "Add note") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /*TODO*/ },
            shape = CircleShape
        ) {
            Text(text = "Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    JetNoteTheme {
        NoteScreen()
    }
}
