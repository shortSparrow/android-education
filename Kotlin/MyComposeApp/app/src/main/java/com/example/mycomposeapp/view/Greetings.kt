package com.example.mycomposeapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greetings(
    onRemoveClicked: (task: TaskItem) -> Unit,
    names: List<TaskItem>,
    onCheckedTask: (TaskItem, Boolean) -> Unit,
) {
    Surface() {
        Column() {
            LazyColumn {
                item { Text(text = "Header") }
                stickyHeader { Text(text = "Lol kek") }
                items(names, key = { task -> task.id }) { name ->
                    Greeting(
                        name = name,
                        onRemoveClicked = onRemoveClicked,
                        onCheckedChange = { checked ->
                            onCheckedTask(
                                name,
                                checked
                            )
                        },
                        checked = name.checked,
                        label = name.label,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ComposablePreview() {
    Greetings(
        names = getTempList(),
        onRemoveClicked = { task -> },
        onCheckedTask = { task, checked ->

        },
    )
}