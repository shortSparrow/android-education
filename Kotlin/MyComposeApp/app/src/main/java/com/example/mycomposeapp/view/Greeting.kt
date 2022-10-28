package com.example.mycomposeapp.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Greeting(
    onCheckedChange: (Boolean) -> Unit,
    onRemoveClicked: (task: TaskItem) -> Unit,
    name: TaskItem,
    checked: Boolean,
    label: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 2000,
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {

                Text(text = label)
            }
            OutlinedButton(onClick = { onRemoveClicked(name) }) {
                Text(text = "Delete")
            }

            Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}