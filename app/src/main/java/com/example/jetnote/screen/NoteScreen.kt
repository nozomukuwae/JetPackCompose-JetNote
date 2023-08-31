package com.example.jetnote.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnote.R
import com.example.jetnote.model.Note
import com.example.jetnote.ui.theme.JetNoteTheme
import java.time.format.DateTimeFormatter

@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

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
            description = description,
            onDescriptionChange = { input ->
                if (input.all { it.isLetter() || it.isWhitespace() }) description = input
            },
            onSave = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""
                    Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        NoteList(notes = notes, onNoteClick = onRemoveNote)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopFields(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
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
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = "Add note") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSave,
            shape = CircleShape
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Note) -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("EEE, d MMM")

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) {
            Card(
                modifier = Modifier.fillMaxWidth().clickable {
                    Log.d("item clicked", it.title)
                    onNoteClick(it)
                },
                shape = RoundedCornerShape(topEnd = 16.dp),
                backgroundColor = Color.LightGray
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = it.entryDate.format(formatter),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    JetNoteTheme {
        NoteScreen(
            notes = listOf(
                Note(title = "first note", description = "description 1"),
                Note(title = "second note", description = "description 2")
            ),
            onAddNote = {},
            onRemoveNote = {}
        )
    }
}
